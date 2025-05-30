package com.DrWait.domain.family.dto;

import com.DrWait.domain.family.entity.FamilyMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberListResponse {
    private Set<MemberResponse> familyMembers;

    public MemberListResponse(Set<FamilyMember> members){
        this.familyMembers = members.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toSet());
    }
}
