package com.DrWait.domain.reservation.controller;

import com.DrWait.domain.family.entity.FamilyGroup;
import com.DrWait.domain.family.entity.FamilyMember;
import com.DrWait.domain.family.service.FamilyGroupService;
import com.DrWait.domain.family.service.FamilyMemberService;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import com.DrWait.domain.reservation.dto.ReservationCancelDto;
import com.DrWait.domain.reservation.dto.ReservationDto;
import com.DrWait.domain.reservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final AuthService authService;
    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FamilyGroupService familyGroupService;
    private final FamilyMemberService familyMemberService;

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

    @PostMapping("/{userId}")
    public ResponseEntity<String> makeFamilyMemberReservation(
            @PathVariable("userId") String userId,
            @RequestBody ReservationDto dto,
            HttpServletRequest request
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null || !jwtTokenProvider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = authService.getUserByBearerToken(token);
        // check my family group (if it existed, my authority is owner)
        FamilyGroup familyGroup = familyGroupService.getGroupByOwner(user);
        // check that user exist in my group
        FamilyMember familyMember = familyMemberService.getMemberByPrimaryKey(familyGroup.getId(), UUID.fromString(userId));

        reservationService.makeReservation(dto, familyMember.getId().getUserId());

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
