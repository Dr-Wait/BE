package com.DrWait.domain.family.service;

import com.DrWait.domain.family.dto.MemberListResponse;
import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.repository.FamilyGroupRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FamilyGroupService {

    private final FamilyGroupRepository familyGroupRepository;

    public FamilyGroup getGroupByOwner (User owner){
        return familyGroupRepository.findByOwner(owner)
                .orElseGet(() ->{
                    FamilyGroup newFamilyGroup = new FamilyGroup(owner);
                    return familyGroupRepository.save(newFamilyGroup);
                });
    }

    public MemberListResponse getGroupMembersByGroupId(Long groupId){
        FamilyGroup familyGroup = familyGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAMILY_GROUP_NOT_FOUND));

        return new MemberListResponse(familyGroup.getMembers());
    }
}
