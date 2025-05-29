package com.DrWait.domain.family.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FamilyMemberId implements Serializable {

    @Column(name = "family_group_id")
    private Long familyGroupId;
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;


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
