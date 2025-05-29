package com.DrWait.domain.family.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FamilyMemberId {

    private UUID userId;
    private Long familyGroupId;
}
