package com.DrWait.domain.hospital.repository;

import com.DrWait.domain.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByUsername(String username);
    Optional<Hospital> findById(UUID id);

    boolean existsByUsername(String username);
}