package com.DrWait.domain.reservation.controller;

import com.DrWait.domain.reservation.dto.ReservationManagementResultDto;
import com.DrWait.global.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationManagementDto;
import com.DrWait.domain.reservation.service.ReservationManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/manage")
    public ResponseEntity<ReservationManagementResultDto> manageReservation(
            @RequestBody ReservationManagementDto dto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        String hospitalIdStr = jwtTokenProvider.getUUID(token);

        UUID hospitalId = UUID.fromString(hospitalIdStr);

        reservationManagementService.manageReservation(dto, hospitalId);

        ReservationManagementResultDto resultDto = reservationManagementService.manageReservation(dto, hospitalId);
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservationManagementResultDto>> getAllReservations(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String hospitalIdStr = jwtTokenProvider.getUUID(token);
        UUID hospitalId = UUID.fromString(hospitalIdStr);

        List<ReservationManagementResultDto> results = reservationManagementService.getReservations(hospitalId);
        return ResponseEntity.ok(results);
    }
}
