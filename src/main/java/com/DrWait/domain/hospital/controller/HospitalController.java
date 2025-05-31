package com.DrWait.domain.hospital.controller;

import com.DrWait.domain.hospital.dto.HospitalInfoResponseDto;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.service.HospitalService;
import com.DrWait.domain.user.service.HospitalPrincipal;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @GetMapping("")
    public ResponseEntity<HospitalInfoResponseDto> getHospitalInfo(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null || !jwtTokenProvider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Hospital hospital = authService.getHospitalByBearerToken(token);
        return ResponseEntity.ok(hospitalService.getHospitalInfo(hospital));
    }
}
