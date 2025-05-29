package com.DrWait.domain.family_example.service;

import com.DrWait.domain.family_example.dto.MemberAddRequest;
import com.DrWait.domain.family_example.dto.MemberResponse;
import com.DrWait.domain.family_example.entity.FamilyGroup;
import com.DrWait.domain.family_example.entity.FamilyMember;
import com.DrWait.domain.family_example.entity.FamilyMemberId;
import com.DrWait.domain.family_example.repository.FamilyGroupRepository;
import com.DrWait.domain.family_example.repository.FamilyMemberRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final FamilyMemberRepository familyMemberRepository;

    @Transactional
    public MemberResponse addMember(UUID ownerId, MemberAddRequest req) {
        // Get owner user from the request
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner user not found"));

        // Check if the user exists
        User memberUser = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new EntityNotFoundException("Member user not found"));

        // Check if the family group exists or create a new one
        FamilyGroup familyGroup = familyGroupRepository.findByOwner(owner)
                .orElseGet(() ->{
                    FamilyGroup newFamilyGroup = new FamilyGroup(owner);
                    return familyGroupRepository.save(newFamilyGroup);
                });

        // Create a new FamilyMemberId using the group and user IDs
        FamilyMemberId primaryKey = new FamilyMemberId(familyGroup.getId(), memberUser.getId());

        // Check if the user is already a member of the family group
        if (familyMemberRepository.existsById(primaryKey)) {
            throw new IllegalStateException("User is already a member of this family group");
        }

        // Create a new FamilyMember entity
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(primaryKey);
        familyMember.setFamilyGroup(familyGroup);
        familyMember.setUser(memberUser);
        familyMember.setRole(req.role());
        familyMember.setConfirmed(false);

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
        FamilyMemberId primaryKey = new FamilyMemberId(groupId, userId);

        // Check if the member exists
        FamilyMember familyMember = familyMemberRepository.findById(primaryKey)
                .orElseThrow(() -> new EntityNotFoundException("Family member not found"));

        // Remove the member from the family group
        FamilyGroup familyGroup = familyMember.getFamilyGroup();
        familyGroup.removeMember(familyMember);

        // DB delete
        familyMemberRepository.delete(familyMember);
    }
}
