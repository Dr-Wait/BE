package com.DrWait.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter @Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "hospital_id", columnDefinition = "BINARY(16)")
    private UUID hospitalId;

    private String role;

    @Column(name = "symptom_id")
    private Long symptomId;

    @Column(name = "first_visit")
    private boolean firstVisit;
    @Column(name = "is_confirmed")
    private boolean isConfirmed;
    @Column(name = "is_completed")
    private boolean isCompleted;

    private String message;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;
}
