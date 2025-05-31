package com.DrWait.domain.family.service;

import com.DrWait.domain.family.dto.MemberAddRequest;
import com.DrWait.domain.family.dto.MemberListResponse;
import com.DrWait.domain.family.dto.MemberResponse;
import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.entity.FamilyMemberId;
import com.DrWait.domain.family.repository.FamilyMemberRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.repository.UserRepository;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final UserRepository userRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyGroupService familyGroupService;

    @Transactional
    public MemberResponse addMember(User owner, MemberAddRequest req) {
        // Check if the user exists
        User memberUser = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // Check if the family group exists or create a new one
        FamilyGroup familyGroup = familyGroupService.getGroupByOwner(owner);

        // Create a new FamilyMemberId using the group and user IDs
        FamilyMemberId primaryKey = new FamilyMemberId(familyGroup.getId(), memberUser.getId());

        // Check if the user is already a member of the family group
        if (familyMemberRepository.existsById(primaryKey)) {
            throw new CustomException(ErrorCode.ALREADY_JOINED_FAMILY);
        }

        // Create a new FamilyMember entity
        FamilyMember familyMember = FamilyMember.builder()
                .id(primaryKey)
                .familyGroup(familyGroup)
                .user(memberUser)
                .role(req.role())
                .isConfirmed(false)
                .build();

        // Save the new FamilyMember entity
        familyGroup.addMember(familyMember);

        // Return the response
        return new MemberResponse(
                familyMember.getId().getFamilyGroupId(),
                familyMember.getId().getUserId(),
                familyMember.getRole(),
                familyMember.isConfirmed()
        );
    }

    @Transactional
    public void removeMember(Long groupId, UUID userId) {
        FamilyMember familyMember = getMemberByPrimaryKey(groupId, userId);

        // Remove the member from the family group
        FamilyGroup familyGroup = familyMember.getFamilyGroup();
        familyGroup.removeMember(familyMember);

        // DB delete
        familyMemberRepository.delete(familyMember);
    }

    public FamilyMember getMemberByPrimaryKey(Long groupId, UUID userId){
        FamilyMemberId primaryKey = new FamilyMemberId(groupId, userId);

        // Check if the member exists
        return familyMemberRepository.findById(primaryKey)
                .orElseThrow(() -> new CustomException(ErrorCode.FAMILY_MEMBER_NOT_FOUND));
    }

    public Optional<FamilyMember> getGroupMembersByUser(User user){
        return familyMemberRepository.findById_UserId(user.getId());
    }

    @Transactional
    public void confirmedMember(Long groupId, User user){
        FamilyMember familyMember = getMemberByPrimaryKey(groupId, user.getId());

        // Check if already joined group
        if(familyMember.isConfirmed()) throw new CustomException(ErrorCode.ALREADY_JOINED_FAMILY);

        familyMember.setConfirmed(true);
        familyMemberRepository.save(familyMember);
    }
}
