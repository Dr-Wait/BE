package com.DrWait.domain.symptom.controller;

import com.DrWait.domain.symptom.dto.SymptomResponse;
import com.DrWait.domain.symptom.service.SymptomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/symptom")
public class SymptomController {

  private final SymptomService symptomService;

  @GetMapping("/names")
  public ResponseEntity<List<SymptomResponse>> getAllSymptoms() { // 메서드명 및 반환 타입 변경
    return ResponseEntity.ok(symptomService.getAllSymptoms());
  }

  @GetMapping("/department")
  public ResponseEntity<List<String>> getDepartmentByName(@RequestParam String name) {
    List<String> departments = symptomService.getDepartmentBySymptomName(name);
    if (departments.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(departments);
  }

  @GetMapping("/department/{department}")
  public ResponseEntity<List<SymptomResponse>> getSymptomsByDepartment( // 메서드명 및 반환 타입 변경
      @PathVariable("department") String department) {
    List<SymptomResponse> symptomResponses = symptomService.getSymptomsByDepartment(department);
    if (symptomResponses.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(symptomResponses);
  }
}

