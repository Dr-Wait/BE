package com.DrWait.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationManagementDto {
    private Long reservationId;
    private Boolean isConfirmed;
    private Boolean isCompleted;
}
