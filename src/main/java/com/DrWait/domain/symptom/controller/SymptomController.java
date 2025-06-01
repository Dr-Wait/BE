package com.DrWait.domain.symptom.controller;

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
  public ResponseEntity<List<String>> getAllSymptomNames() {
    return ResponseEntity.ok(symptomService.getAllSymptomNames());
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
  public ResponseEntity<List<String>> getSymptomNamesByDepartment(
      @PathVariable("department") String department) {
    List<String> symptomNames = symptomService.getSymptomNamesByDepartment(department);
    if (symptomNames.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(symptomNames);
  }
}

