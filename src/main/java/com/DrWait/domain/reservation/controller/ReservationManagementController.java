package com.DrWait.domain.reservation.controller;

import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.reservation.dto.ReservationManagementResultDto;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationManagementDto;
import com.DrWait.domain.reservation.service.ReservationManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class ReservationManagementController {

    private final ReservationManagementService reservationManagementService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PatchMapping("/manage")
    public ResponseEntity<ReservationManagementResultDto> manageReservation(
            @RequestBody ReservationManagementDto dto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Hospital hospital = authService.getHospitalByBearerToken(token);

        reservationManagementService.manageReservation(dto, hospital.getId());

        ReservationManagementResultDto resultDto = reservationManagementService.manageReservation(dto, hospital.getId());
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservationManagementResultDto>> getAllReservations(HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Hospital hospital = authService.getHospitalByBearerToken(token);

        List<ReservationManagementResultDto> results = reservationManagementService.getReservations(hospital.getId());
        return ResponseEntity.ok(results);
    }
}
