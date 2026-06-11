package org.example.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Service
@Builder
public class RegisterRequest {
    @NotBlank(message = "Không để trống tên")
    private String username;
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotBlank(message = "Không để trống mật khẩu")
    private String password;
    @NotBlank(message = "Không để trống họ và tên")
    private String fullName;
}
