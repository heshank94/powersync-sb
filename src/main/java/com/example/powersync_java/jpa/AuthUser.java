package com.example.powersync_java.jpa;

/**
 * @author Heshan Karunaratne
 */

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Column(name = "is_superuser", nullable = false)
    private boolean isSuperuser;

    @Column(length = 150, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 150, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 150, nullable = false)
    private String lastName;

    @Column(length = 254, nullable = false)
    private String email;

    @Column(name = "is_staff", nullable = false)
    private boolean isStaff;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "date_joined", nullable = false)
    private OffsetDateTime dateJoined;

    @PrePersist
    public void prePersist() {
        dateJoined = OffsetDateTime.now();
    }
}
