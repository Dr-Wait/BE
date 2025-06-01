package com.DrWait.domain.symptom.repository;

import com.DrWait.domain.symptom.entity.SymptomEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends JpaRepository<SymptomEntity, Long> {

  @Query("SELECT s.name FROM SymptomEntity s")
  List<String> findAllSymptomNames();

  List<SymptomEntity> findAllByName(String name); // 여기 변경!

  @Query("SELECT s.name FROM SymptomEntity s WHERE s.department = :department")
  List<String> findNamesByDepartment(@Param("department") String department);
}
