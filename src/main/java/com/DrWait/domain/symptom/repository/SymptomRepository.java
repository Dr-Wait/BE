package com.DrWait.domain.symptom.repository;

import com.DrWait.domain.symptom.entity.SymptomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<SymptomEntity, Long> {

    @Query("SELECT s.name FROM SymptomEntity s")
    List<String> findAllSymptomNames();

    List<SymptomEntity> findAllByName(String name); // 여기 변경!
}
