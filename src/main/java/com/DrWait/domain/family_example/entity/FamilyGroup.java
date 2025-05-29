package com.DrWait.domain.family_example.entity;

import java.util.Set;
import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.DrWait.domain.user.entity.User;

//CREATE TABLE `family_group` (
//        `id` bigint(20) NOT NULL AUTO_INCREMENT,
//  `user_id` bigint(20) NOT NULL,
//PRIMARY KEY (`id`),
//KEY `user_id` (`user_id`),
//CONSTRAINT `family_group_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)  ON DELETE CASCADE ON UPDATE CASCADE
//) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

@Entity
@Getter @Setter
@Table(name = "family_group")
public class FamilyGroup {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id; // id bigint(20) NOT NULL AUTO_INCREMENT

    // One-to-One relationship
    @OneToOne(fetch = FetchType.LAZY) // FetchType.LAZY for performance optimization
    @JoinColumn(name = "user_id", nullable = false) // bigint(20) NOT NULL
    private User owner; // user_id bigint(20) NOT NULL

    // One-to-Many relationship
    @Getter
    @OneToMany(mappedBy = "familyGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<FamilyMember> members = new HashSet<>(); // family_member_id bigint(20) NOT NULL

    public FamilyGroup() {}

    public FamilyGroup(User owner) {
        this.owner = owner;
    }

    public void addMember(FamilyMember member) {
        members.add(member);
        member.setFamilyGroup(this);
    }

    public void removeMember(FamilyMember member) {
        members.remove(member);
        member.setFamilyGroup(null);
    }
}
