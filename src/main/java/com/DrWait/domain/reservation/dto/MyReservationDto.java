package com.DrWait.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyReservationDto {

    private String userFullname;
    private String role;
    private boolean firstVisit;
    private String message;
    private String symptomName;
    private String reservationTime;
    private boolean completed;
    private boolean confirmed;
    private int waitingOrder;
    private int waitingTime;
}
