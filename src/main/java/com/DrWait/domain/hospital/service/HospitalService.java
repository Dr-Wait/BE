package com.DrWait.domain.hospital.service;

import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.repository.HospitalRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Hospital getHospitalEntityById(String userId){
        return hospitalRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public Hospital getHospitalEntityByUsername(String username){
        return hospitalRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public HospitalInfoResponseDto getHospitalInfo(String hospitalId){
        return new HospitalInfoResponseDto(getHospitalEntityById(hospitalId));
    }
}
