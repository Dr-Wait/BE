package com.DrWait.domain.family.repository;

import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {
    Optional<FamilyGroup> findByOwner(User owner);
}
