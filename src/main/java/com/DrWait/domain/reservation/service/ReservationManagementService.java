package com.DrWait.domain.reservation.service;

import com.DrWait.domain.reservation.dto.ReservationManagementDto;
import com.DrWait.domain.reservation.entity.ReservationEntity;
import com.DrWait.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationManagementService {

    private final ReservationRepository reservationRepository;

    public void manageReservation(ReservationManagementDto dto, UUID hospitalId) {
        ReservationEntity reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));

        if (Boolean.TRUE.equals(dto.getIsConfirmed())) {
            reservation.setConfirmed(true);
            reservation.setReservationTime(LocalDateTime.now());
        }

        if (Boolean.TRUE.equals(dto.getIsCompleted())) {
            reservation.setCompleted(true);
        }

        reservationRepository.save(reservation);
    }

}
