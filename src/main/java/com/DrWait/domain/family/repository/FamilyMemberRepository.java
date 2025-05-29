package com.DrWait.domain.family.repository;

import com.DrWait.domain.family.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Optional<FamilyMember> findByUserId(UUID userId);

    Boolean existsById_UserId(UUID userId);
}
