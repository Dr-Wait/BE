package com.DrWait.domain.family.service;

import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.entity.FamilyMemberId;
import com.DrWait.domain.family.enums.FamilyRole;
import com.DrWait.domain.family.repository.FamilyGroupRepository;
import com.DrWait.domain.family.repository.FamilyMemberRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.service.UserService;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import com.DrWait.global.security.auth.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyGroupRepository familyGroupRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final AuthService authService;
    private final UserService userService;

    @Transactional
    public FamilyGroup createFamilyGroup(UUID userId){
        FamilyGroup familyGroup = FamilyGroup.builder()
                .userId(userId)
                .build();

        return familyGroupRepository.save(familyGroup);
    }

    @Transactional
    public FamilyMember createFamilyMember(UUID userId, Long groupId){
        FamilyMemberId id = new FamilyMemberId(userId, groupId);
        FamilyMember familyMember = FamilyMember.builder()
                .id(id)
                .role(FamilyRole.GUEST)
                .isConfirmed(false)
                .build();

        return familyMemberRepository.save(familyMember);
    }

    @Transactional
    public void updateFamilyMemberRole(String bearerRefreshToken, Long groupId) {
        String userId = authService.getUserIdByBearerToken(bearerRefreshToken);

        FamilyMember familyMember = getFamilyMemberEntityByUserId(userId);
        familyMember.setConfirmed(true);
        familyMember.setRole(FamilyRole.MEMBER);

        familyMemberRepository.save(familyMember);
    }

    public FamilyMember getFamilyMemberEntityByUserId(String userId){
        return familyMemberRepository.findByUserId(UUID.fromString(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.FAMILY_MEMBER_NOT_FOUND));
    }

    public FamilyGroup getFamilyGroupEntityByGroupId(Long groupId){
        return familyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAMILY_GROUP_NOT_FOUND));
    }

    public boolean isOwner(String bearerRefreshToken, Long groupId) {
        String userId = authService.getUserIdByBearerToken(bearerRefreshToken);

        return getFamilyGroupEntityByGroupId(groupId).getUserId().toString().equals(userId);
    }

    public void inviteFamilyMember(String username, Long groupId) {
        User user = userService.getUserEntityByUsername(username);

        // 이미 속해있는 Family Group 이 있는지 확인
        if(checkAlreadyJoinFamilyMember(user.getId().toString())){
            throw new CustomException(ErrorCode.ALREADY_JOINED_FAMILY);
        }

        createFamilyMember(user.getId(), groupId);
    }

    public boolean checkAlreadyJoinFamilyMember(String userId) {
        return familyMemberRepository.existsById_UserId(UUID.fromString(userId));
    }
}
