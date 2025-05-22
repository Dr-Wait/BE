package com.DrWait.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    // 로그인 식별자
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
