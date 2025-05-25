package com.DrWait.domain.hospital.service;

import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalInfoResponseDto getHospitalInfo(String hospitalId){
        Hospital hospital = hospitalRepository.findById(UUID.fromString(hospitalId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new HospitalInfoResponseDto(hospital);
    }
}
