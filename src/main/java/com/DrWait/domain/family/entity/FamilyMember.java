package com.DrWait.domain.family.entity;

import com.DrWait.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
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
    private boolean isConfirmed = false;
}