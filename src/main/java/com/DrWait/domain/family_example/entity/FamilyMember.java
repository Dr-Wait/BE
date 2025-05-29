package com.DrWait.domain.family_example.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.DrWait.domain.user.entity.User;

//CREATE TABLE `family_member` (
//        `user_id` bigint(20) NOT NULL,
//  `family_group_id` bigint(20) NOT NULL,
//  `role` varchar(255) NOT NULL,
//  `is_confirmed` tinyint(1) NOT NULL DEFAULT 0,
//CHECK (is_confirmed IN (0,1)),
//PRIMARY KEY (`user_id`),
//KEY `family_group_id` (`family_group_id`),
//CONSTRAINT `family_member_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
//CONSTRAINT `family_member_ibfk_2` FOREIGN KEY (`family_group_id`) REFERENCES `family_group` (`id`)  ON DELETE CASCADE ON UPDATE CASCADE
//) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

@Entity
@Getter @Setter
@Table(name = "family_member")
public class FamilyMember {
    @EmbeddedId // Composite primary key
    private FamilyMemberId id;

    @MapsId("familyGroupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_group_id", nullable = false)
    private FamilyGroup familyGroup;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String role;

    @Column(name = "is_confirmed",
            nullable = false,
            columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isConfirmed = false; // DEFAULT 0

    public FamilyMember() {
    }

    public FamilyMember(FamilyMemberId id, FamilyGroup familyGroup, User user, String role) {
        this.id = id;
        this.familyGroup = familyGroup;
        this.user = user;
        this.role = role;
    }
}
