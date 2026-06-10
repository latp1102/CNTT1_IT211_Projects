package org.example.project.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.example.project.entity.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdateRequest {
    @Email
    private String email;
    private String fullName;
    private String username;
    private Role role;
    private Boolean enabled;

}
