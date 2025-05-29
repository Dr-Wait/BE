package com.DrWait.domain.family_example.repository;

import com.DrWait.domain.family_example.entity.FamilyMember;
import com.DrWait.domain.family_example.entity.FamilyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, FamilyMemberId> {
}
