package com.DrWait.domain.reservation.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class ReservationDto {
    private UUID hospitalId;
    // private Long userId; // or access token? 토큰 받아와야되나? -> 토큰
    private String role;
    private Long symptomId;
    private boolean firstVisit;
    private String message;
    private LocalDateTime reservationTime;
}
