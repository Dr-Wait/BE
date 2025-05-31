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
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 225)
    private String password;

    @Column(nullable = false, length = 20)
    private String fullname;

    @Column(name = "phone_number", nullable = true, length = 20)
    private String phoneNumber;

    @Column(name = "resident_registration_number", nullable = true, length = 20)
    private String residentRegistrationNumber;

    @Column(name = "profile_image_url", nullable = true, length = 225)
    private String profileImageUrl;

    @Column(nullable = true, length = 225)
    private String payment;
}
