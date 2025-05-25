package com.DrWait.domain.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false, length = 225)
    private String department;

    @Column(nullable = false, length = 225)
    private String address;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(name = "website_url", length = 225)
    private String websiteUrl;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 225)
    private String password;
}
