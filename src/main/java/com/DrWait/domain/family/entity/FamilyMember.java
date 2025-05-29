package com.DrWait.domain.family.entity;

import com.DrWait.domain.family.enums.FamilyRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "family_member")
public class FamilyMember {

    @EmbeddedId
    private FamilyMemberId id;

    @Enumerated(EnumType.STRING)
    private FamilyRole role;

    private boolean isConfirmed;
}