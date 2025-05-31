package com.DrWait.domain.reservation.controller;

import com.DrWait.core.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationManagementDto;
import com.DrWait.domain.reservation.service.ReservationManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class ReservationManagementController {

    private final ReservationManagementService reservationManagementService;
    private final JwtTokenProvider jwtTokenProvider;

    @PatchMapping("/manage")
    public ResponseEntity<Void> manageReservation(
            @RequestBody ReservationManagementDto dto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        String hospitalIdStr = jwtTokenProvider.getUserId(token);
        UUID hospitalId = UUID.fromString(hospitalIdStr);

        reservationManagementService.manageReservation(dto, hospitalId);

        return ResponseEntity.ok().build();
    }
}
