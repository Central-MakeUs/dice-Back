package com.cmc.dice.domain.reservation.application;

import com.cmc.dice.domain.reservation.dao.ReservationRepository;
import com.cmc.dice.domain.reservation.domain.Reservation;
import com.cmc.dice.domain.reservation.dto.*;
import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.exception.SpaceNotFoundException;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;

    // 예약
    public ReservationDto reserve(User user, ReservationRequest reservationRequest) {
        // 예약 확인
        Space space = spaceRepository.findById(reservationRequest.getSpaceId())
                .orElseThrow(SpaceNotFoundException::new);

        // 예약 날짜 중복 체크
        boolean isOverlapping = reservationRepository.existsOverlappingReservation(
                reservationRequest.getSpaceId(), reservationRequest.getStartDate(), reservationRequest.getEndDate()
        );

        if (isOverlapping) {
            throw new IllegalArgumentException("이미 예약된 날짜입니다.");
        }

        Reservation reservation = reservationRepository.save(reservationRequest.toEntity(user, space));

        return ReservationDto.of(reservation);
    }

    // 공간의 예약된 날짜 확인
    public ReservationAvailableDto getAvailableDates(Long spaceId) {
        List<DateDto> reservedDates = reservationRepository.findReservedDatesBySpaceId(spaceId);
        return ReservationAvailableDto.builder()
                .reservedDates(reservedDates)
                .build();
    }

    //자신의 예약 조회
    public List<ReservationInfoDto> getMyReservations(User user) {
        List<Reservation> reservations = reservationRepository.findByUserId(user.getId());

        return reservations.stream()
                .map(this::getReservationInfoDto)
                .toList();
    }

    private ReservationInfoDto getReservationInfoDto(Reservation reservation) {
        return ReservationInfoDto.builder()
                .reservationId(reservation.getId())
                .spaceName(reservation.getSpace().getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .message(reservation.getMessage())
                .build();
    }
}
