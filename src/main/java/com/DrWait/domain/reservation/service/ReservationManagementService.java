package com.DrWait.domain.reservation.service;

import com.DrWait.domain.reservation.dto.ReservationManagementDto;
import com.DrWait.domain.reservation.dto.ReservationManagementResultDto;
import com.DrWait.domain.reservation.entity.ReservationEntity;
import com.DrWait.domain.reservation.repository.ReservationRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationManagementService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationManagementResultDto manageReservation(ReservationManagementDto dto, UUID hospitalId) {
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

        User user = userRepository.findById(reservation.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        String userName = user.getFullname();
        String userPhone = user.getPhoneNumber();
        String lastFour = userPhone.length() >= 4 ? userPhone.substring(userPhone.length() - 4) : userPhone;

        int waitingOrder = reservationRepository
                .countByHospitalIdAndIsConfirmedTrueAndIsCompletedFalseAndReservationTimeBefore(
                        hospitalId,
                        reservation.getReservationTime()
                ) + 1;

        return new ReservationManagementResultDto(waitingOrder, userName, lastFour);
    }

    public List<ReservationManagementResultDto> getReservations(UUID hospitalId) {
        List<ReservationEntity> reservations = reservationRepository
                .findByHospitalIdAndIsConfirmedTrueAndIsCompletedFalse(hospitalId);

        reservations.sort(Comparator.comparing(ReservationEntity::getReservationTime));

        List<ReservationManagementResultDto> resultList = new ArrayList<>();
        int index = 1;

        for (ReservationEntity reservation : reservations) {
            System.out.println("예약 ID: " + reservation.getId());
            System.out.println("예약된 userId: " + reservation.getUserId());

            User user = userRepository.findById(reservation.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

            System.out.println("가져온 유저 이름: " + user.getFullname());
            System.out.println("가져온 전화번호: " + user.getPhoneNumber());


            String userName = user.getFullname();
            String userPhone = user.getPhoneNumber();
            String lastFour = userPhone.length() >= 4 ? userPhone.substring(userPhone.length() - 4) : userPhone;

            resultList.add(new ReservationManagementResultDto(index++, userName, lastFour));
        }

        return resultList;
    }
}
