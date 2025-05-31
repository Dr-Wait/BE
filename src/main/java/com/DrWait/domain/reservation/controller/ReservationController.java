package com.DrWait.domain.reservation.controller;

import com.DrWait.domain.reservation.dto.MyReservationDto;
import com.DrWait.domain.reservation.dto.ReservationResultDto;
import com.DrWait.domain.reservation.entity.ReservationEntity;
import com.DrWait.global.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationCancelDto;
import com.DrWait.domain.reservation.dto.ReservationDto;
import com.DrWait.domain.reservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ReservationResultDto> makeReservation(
            @RequestBody ReservationDto dto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        String userIdStr = jwtTokenProvider.getUUID(token);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = UUID.fromString(userIdStr);

        ReservationResultDto result = reservationService.makeReservation(dto, userId);

        return ResponseEntity.ok(result);  // 여기서 waitingOrder, waitingTime 내려감!
    }

    @PostMapping("/reservation_cancel")
    public ResponseEntity<String> cancelReservation(
            @RequestBody ReservationCancelDto cancelDto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        String userIdStr = jwtTokenProvider.getUUID(token);
        UUID userId = UUID.fromString(userIdStr);

        reservationService.cancelReservation(cancelDto.getReservationId());

        return ResponseEntity.ok("예약 취소 완료!");
    }

    @GetMapping("/my_reservation")
    public ResponseEntity<List<MyReservationDto>> getMyReservations(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String userIdStr = jwtTokenProvider.getUUID(token);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = UUID.fromString(userIdStr);

        List<MyReservationDto> reservationDetails = reservationService.getUserReservationDetails(userId);
        return ResponseEntity.ok(reservationDetails);
    }

}