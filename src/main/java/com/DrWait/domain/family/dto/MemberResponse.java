package com.DrWait.domain.family.dto;

import com.DrWait.domain.family.entity.FamilyMember;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MemberResponse(
        Long familyGroupId,
        UUID userId,
        String fullname,
        String phoneNumber,
        String role,
        boolean isConfirmed
) {

    public static MemberResponse from(FamilyMember member){
        return MemberResponse.builder()
                .familyGroupId(member.getFamilyGroup().getId())
                .userId(member.getId().getUserId())
                .fullname(member.getUser().getFullname())
                .phoneNumber(member.getUser().getPhoneNumber())
                .role(member.getRole())
                .isConfirmed(member.isConfirmed())
                .build();
    }
}
