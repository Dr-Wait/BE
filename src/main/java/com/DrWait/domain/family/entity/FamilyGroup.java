package com.DrWait.domain.family.entity;

import com.DrWait.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "family_group")
public class FamilyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Getter
    @OneToMany(mappedBy = "familyGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<FamilyMember> members = new HashSet<>();

    public FamilyGroup(User owner) {
        this.owner = owner;
    }

    public FamilyGroup(Set<FamilyMember> members) {
        this.members = members;
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
