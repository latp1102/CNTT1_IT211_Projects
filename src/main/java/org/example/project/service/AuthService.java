package org.example.project.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.*;
import org.example.project.dto.response.LoginResponse;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.entity.*;
import org.example.project.exception.UserNotFoundException;
import org.example.project.repository.RefreshTokenRepository;
import org.example.project.repository.TokenBlackistRepository;
import org.example.project.repository.UserRepository;
import org.example.project.service.Jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlackistRepository tokenBlackistRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("người dùng đã tồn tại");
        }
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(RoleEntity.builder().role(Role.ROLE_CUSTOMER).build());
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.ROLE_CUSTOMER)
                .enabled(true)
                .build();
        userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("user không tồn tại"));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(token);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("token không tồn tại"));
        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token hết hạn");
        }
        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
    public void logout(String token){
        LocalDateTime expireAt = jwtService.extractExpiration(token)
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
        TokenBlacklist tokenBlacklist = TokenBlacklist.builder()
                .token(token)
                .expiryDate(expireAt)
                .build();
        tokenBlackistRepository.save(tokenBlacklist);
    }
    public void changePassword(ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại"));
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
    public String forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại"));
        String newPassword = UUID.randomUUID().toString().substring(0,8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return newPassword;
    }
}
