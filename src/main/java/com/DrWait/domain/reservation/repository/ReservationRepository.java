package com.DrWait.domain.reservation.repository;

import com.DrWait.domain.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    int countByHospitalIdAndIsConfirmedTrueAndIsCompletedFalseAndReservationTimeBefore(
            UUID hospitalId, LocalDateTime reservationTime
    );
    List<ReservationEntity> findByHospitalIdAndIsConfirmedTrueAndIsCompletedFalse(UUID hospitalId);
    List<ReservationEntity> findAllByUserId(UUID userId);
}