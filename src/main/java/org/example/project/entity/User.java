package org.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static org.example.project.entity.Role.ROLE_CUSTOMER;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fullName;
    @Builder.Default
    private Boolean enabled = true;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = ROLE_CUSTOMER;
}
