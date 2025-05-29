package com.DrWait.domain.family_example.repository;

import com.DrWait.domain.family_example.entity.FamilyGroup;
import com.DrWait.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {
    Optional<FamilyGroup> findByOwner(User owner);
}
