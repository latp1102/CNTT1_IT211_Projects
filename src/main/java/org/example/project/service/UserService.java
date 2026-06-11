package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.UserCreateRequest;
import org.example.project.dto.response.UserResponse;
import org.example.project.dto.request.UserUpdateRequest;
import org.example.project.entity.User;
import org.example.project.exception.UserNotFoundException;
import org.example.project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public Page<UserResponse> searchUsers(String keyword, int page, int size){
        return userRepository.findByUsernameContainingIgnoreCase(keyword, PageRequest.of(page, size)).map(UserResponse::fromEntity);

    }
    public UserResponse findById(Long id){
        return userRepository.findById(id)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại"));
    }
    public UserResponse create(UserCreateRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .enabled(true)
                .build();
        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }
    public UserResponse update(Long id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User không tồn tại"));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        user.setEnabled(request.getEnabled());
        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }
    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User không tồn tại"));
        userRepository.delete(user);
    }
}
