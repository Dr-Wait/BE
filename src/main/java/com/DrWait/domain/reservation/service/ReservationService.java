package com.DrWait.domain.reservation.service;

import com.DrWait.domain.reservation.dto.ReservationDto;
import com.DrWait.domain.reservation.dto.ReservationResultDto;
import com.DrWait.domain.reservation.entity.ReservationEntity;
import com.DrWait.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

// 순서 계산 (쿼리) -> is_confirmed && !is_completed order by reservation_time asc
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResultDto makeReservation(ReservationDto dto, UUID userId) {
        ReservationEntity entity = new ReservationEntity();
        entity.setHospitalId(dto.getHospitalId());
        entity.setUserId(userId);
        entity.setRole(dto.getRole());
        entity.setSymptomId(dto.getSymptomId());
        entity.setFirstVisit(dto.isFirstVisit());
        entity.setConfirmed(false);   // 처음 생성 시 false
        entity.setCompleted(false);   // 처음 생성 시 false
        entity.setMessage(dto.getMessage());
        entity.setReservationTime(LocalDateTime.now());

        reservationRepository.save(entity);

        int waitingOrder = reservationRepository
                .countByHospitalIdAndIsConfirmedTrueAndIsCompletedFalseAndReservationTimeBefore(
                        entity.getHospitalId(),
                        entity.getReservationTime()
                ) +1;

        int waitingTime = (waitingOrder - 1) * 5;
        if (waitingOrder == 0) {
            waitingTime = 0;
        }

        return new ReservationResultDto(waitingOrder, waitingTime);
    }
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
