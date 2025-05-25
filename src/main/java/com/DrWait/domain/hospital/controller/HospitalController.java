package com.DrWait.domain.hospital.controller;

import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.service.HospitalService;
import com.DrWait.domain.user.service.HospitalPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hospital/info")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("")
    public ResponseEntity<HospitalInfoResponseDto> getHospitalInfo(@AuthenticationPrincipal HospitalPrincipal hospital){
        return ResponseEntity.ok(hospitalService.getHospitalInfo(hospital.getUsername()));
    }
}
