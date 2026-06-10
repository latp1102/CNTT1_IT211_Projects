package org.example.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.project.entity.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {
    @NotBlank(message = "username không để trống")
    private String username;
    @Email
    @NotBlank(message = "email không để trống")
    private String email;
    @NotBlank(message = "password không để trống")
    private String password;
    @NotBlank(message = "fullName không để trống")
    private String fullName;
    private Role role;
}
