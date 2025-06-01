package com.DrWait.domain.symptom.service;

import com.DrWait.domain.symptom.dto.SymptomResponse;
import com.DrWait.domain.symptom.entity.SymptomEntity;
import com.DrWait.domain.symptom.repository.SymptomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SymptomService {

  private final SymptomRepository symptomRepository;

  public List<SymptomResponse> getAllSymptoms() {
    return symptomRepository.findAllSymptomNames().stream()
        .map(symptom -> new SymptomResponse(symptom.getId(), symptom.getName()))
        .collect(Collectors.toList());
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

  public List<SymptomResponse> getSymptomsByDepartment(String department) {
    return symptomRepository.findSymptomsByDepartment(department).stream()
        .map(symptom -> new SymptomResponse(symptom.getId(), symptom.getName()))
        .collect(Collectors.toList());
  }
}

