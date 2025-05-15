package com.DrWait;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    void deleteByName(String name);
    TeamEntity findByName(String name);
}
