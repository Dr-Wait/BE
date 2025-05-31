package com.DrWait.domain.symptom.controller;

import com.DrWait.domain.symptom.service.SymptomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}

