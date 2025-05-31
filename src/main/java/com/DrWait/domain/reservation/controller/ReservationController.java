package com.DrWait.domain.reservation.controller;

import com.DrWait.core.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationCancelDto;
import com.DrWait.domain.reservation.dto.ReservationDto;
import com.DrWait.domain.reservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> makeReservation(
            @RequestBody ReservationDto dto,
            HttpServletRequest request
    ) {
        // 토큰 userId 추출
        String token = jwtTokenProvider.resolveToken(request);
        String userIdStr = jwtTokenProvider.getUserId(token);
        UUID userId = UUID.fromString(userIdStr);

        reservationService.makeReservation(dto, userId);

        return ResponseEntity.ok("예약 완료!");
    }

    @PostMapping("/reservation_cancel")
    public ResponseEntity<String> cancelReservation(
            @RequestBody ReservationCancelDto cancelDto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        String userIdStr = jwtTokenProvider.getUserId(token);
        UUID userId = UUID.fromString(userIdStr);

        reservationService.cancelReservation(cancelDto.getReservationId());

        return ResponseEntity.ok("예약 취소 완료!");
    }
}
