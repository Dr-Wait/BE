package com.DrWait.domain.family_example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Embeddable
@Getter @Setter
public class FamilyMemberId implements Serializable {
    @Column(name = "family_group_id")
    private Long familyGroupId; // bigint(20) NOT NULL
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId; // BINARY(16) NOT NULL

    public FamilyMemberId() {
    }

    public FamilyMemberId(Long familyGroupId, UUID userId) {
        this.familyGroupId = familyGroupId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMemberId that = (FamilyMemberId) o;
        return Objects.equals(familyGroupId, that.familyGroupId) &&
               Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyGroupId, userId);
    }
}
