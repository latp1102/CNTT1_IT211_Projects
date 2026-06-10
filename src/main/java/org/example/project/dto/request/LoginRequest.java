package org.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank(message = "Không để trống tên")
    private String username;
    @NotBlank(message = "Không để trống mật khẩu")
    private String password;
}
