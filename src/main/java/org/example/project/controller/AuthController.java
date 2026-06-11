package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.*;
import org.example.project.dto.response.LoginResponse;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return new ResponseEntity<>(ResponseDTO.<Void>builder().success(true).message("đăng kí thành công").build(), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return new ResponseEntity<>(ResponseDTO.<LoginResponse>builder().success(true).message("đăng nhập thành công").data(response).build(), HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDTO<LoginResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request);
        return new ResponseEntity<>(ResponseDTO.<LoginResponse>builder().success(true).message("refresh token thành công").data(response).build(), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("khong đủ để ủy quyền");
        }
        String token = header.substring(7);
        authService.logout(token);
        return new ResponseEntity<>(ResponseDTO.<Void>builder().success(true).message("logout thành công").build(), HttpStatus.OK);
    }
    @PutMapping("/change-password")
    public ResponseEntity<ResponseDTO<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        authService.changePassword(request);
        return ResponseEntity.ok(ResponseDTO.<Void>builder()
                .success(true)
                .message("thay đổi mật khẩu thành công")
                .build()
        );
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        authService.forgotPassword(request);
        return ResponseEntity.ok(ResponseDTO.<String>builder()
                .success(true)
                .message("mật khẩu mới được tạo")
                .data(authService.forgotPassword(request))
                .build()
        );
    }

}
