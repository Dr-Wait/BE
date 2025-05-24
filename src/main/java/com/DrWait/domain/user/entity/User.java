package com.DrWait.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "user_email",
            nullable = false,
            unique = true,
            length = 50
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "resident_registration_number", nullable = true)
    private String residentRegistrationNumber;

    @Column(name = "profile_image_url", nullable = true)
    private String profileImageUrl;

    @Column(nullable = true)
    private String payment;
}
