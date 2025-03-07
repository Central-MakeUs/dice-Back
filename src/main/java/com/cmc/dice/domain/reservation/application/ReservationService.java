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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<ReservationInfoDto> getMyReservations(User user,String status, int page, int size) {
        Page<Reservation> reservations = reservationRepository.findByUserIdAndStatus(user.getId(), status, PageRequest.of(page, size));
        return reservations.map(this::getReservationInfoDto);
    }

    private ReservationInfoDto getReservationInfoDto(Reservation reservation) {
        Space space = reservation.getSpace();
        int totalPrice = (reservation.getEndDate().getDayOfYear() - reservation.getStartDate().getDayOfYear()) * space.getDiscountPrice();

        return ReservationInfoDto.builder()
                .reservationId(reservation.getId())
                .spaceName(space.getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .message(reservation.getMessage())
                .status(reservation.getStatus())
                .city(space.getCity())
                .district(space.getDistrict())
                .capacity(space.getCapacity())
                .size(space.getSize())
                .totalPrice(totalPrice)
                .spaceImage(space.getImageUrls().get(0))
                .build();
    }

    public void cancelReservation(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("예약자만 예약을 취소할 수 있습니다.");
        }

        reservation.cancel();

        reservationRepository.save(reservation);
    }

    public void declineReservation(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        if (!reservation.getSpace().getAdmin().getId().equals(user.getId())) {
            throw new IllegalArgumentException("호스트만 예약을 거절할 수 있습니다.");
        };

        reservation.decline();

        reservationRepository.save(reservation);
    }

    public void acceptReservation(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        if (!reservation.getSpace().getAdmin().getId().equals(user.getId())) {
            throw new IllegalArgumentException("호스트만 예약을 거절할 수 있습니다.");
        };

        reservation.accept();

        reservationRepository.save(reservation);
    }
}
