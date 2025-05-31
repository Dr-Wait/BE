package com.DrWait.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationResultDto{
    // waiting 순서
    // waiting time -> (순서-1)*5분
    // 현재시간, 진료시간?
    private int waitingOrder;
    private int waitingTime;
}
