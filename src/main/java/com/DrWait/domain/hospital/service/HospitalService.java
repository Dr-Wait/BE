package com.DrWait.domain.hospital.service;

import com.DrWait.domain.hospital.dto.HospitalInfoResponse;
import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.repository.HospitalRepository;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalService {

  private final HospitalRepository hospitalRepository;

  public List<HospitalInfoResponse> getHospitalList() {
    return hospitalRepository.findAll().stream()
        .map(hospital -> new HospitalInfoResponse(
            hospital.getId(),
            hospital.getName(),
            hospital.getDepartment(),
            hospital.getAddress(),
            hospital.getTelephone(),
            hospital.getWebsiteUrl()))
        .toList();
  }

  public Hospital getHospitalEntityById(String userId) {
    return hospitalRepository.findById(UUID.fromString(userId))
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  public Hospital getHospitalEntityByUsername(String username) {
    return hospitalRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  public HospitalInfoResponseDto getHospitalInfo(Hospital hospital) {
    return new HospitalInfoResponseDto(getHospitalEntityById(hospital.getId().toString()));
  }
}
