package com.DrWait.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationManagementResultDto {
    private int waitingOrder;
    private String userName;
    private String userPhoneLastFour;
}