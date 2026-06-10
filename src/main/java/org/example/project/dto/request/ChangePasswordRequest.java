package org.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "oldPassword không để trống")
    private String oldPassword;
    @NotBlank(message = "newPassword không để trống")
    @Size(min = 6, max = 50)
    private String newPassword;
}
