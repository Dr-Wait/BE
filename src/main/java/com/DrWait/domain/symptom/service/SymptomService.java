package com.DrWait.domain.symptom.service;

import com.DrWait.domain.symptom.entity.SymptomEntity;
import com.DrWait.domain.symptom.repository.SymptomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SymptomService {

  private final SymptomRepository symptomRepository;

  public List<String> getAllSymptomNames() {
    return symptomRepository.findAllSymptomNames();
  }

  public List<String> getDepartmentBySymptomName(String name) {
    List<SymptomEntity> symptoms = symptomRepository.findAllByName(name);
      if (symptoms.isEmpty()) {
          return List.of();
      }

    return symptoms.stream()
        .map(SymptomEntity::getDepartment)
        .distinct() // 중복 제거 (원하면 빼도 됨)
        .toList();
  }

  public List<String> getSymptomNamesByDepartment(String department) {
    return symptomRepository.findNamesByDepartment(department);
  }
}

