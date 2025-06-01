package com.DrWait.domain.hospital.controller;

import com.DrWait.domain.hospital.dto.HospitalInfoResponse;
import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.service.HospitalService;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/hospital")
@RequiredArgsConstructor
public class HospitalController {

  private final HospitalService hospitalService;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthService authService;

  @GetMapping("/{id}")
  public ResponseEntity<HospitalInfoResponse> getHospitalInfo(
      @PathVariable("id") String hospitalId) {
    Hospital hospital = hospitalService.getHospitalEntityById(hospitalId);
    if (hospital == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    HospitalInfoResponse response = new HospitalInfoResponse(
        hospital.getId(),
        hospital.getName(),
        hospital.getDepartment(),
        hospital.getAddress(),
        hospital.getTelephone(),
        hospital.getWebsiteUrl()
    );

    return ResponseEntity.ok(response);
  }

  @GetMapping("/list")
  public ResponseEntity<List<HospitalInfoResponse>> getHospitalList() {
    return ResponseEntity.ok(hospitalService.getHospitalList());
  }

  @GetMapping("/info")
  public ResponseEntity<HospitalInfoResponseDto> getHospitalInfo(HttpServletRequest request) {
    String token = jwtTokenProvider.resolveToken(request);
    if (token == null || !jwtTokenProvider.validateToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Hospital hospital = authService.getHospitalByBearerToken(token);

    return ResponseEntity.ok(hospitalService.getHospitalInfo(hospital));
  }
}
