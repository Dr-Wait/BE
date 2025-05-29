package com.DrWait.domain.family.repository;

import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.entity.FamilyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, FamilyMemberId> {
}
