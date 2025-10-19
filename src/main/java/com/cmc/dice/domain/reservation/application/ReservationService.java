package com.cmc.dice.domain.reservation.application;

import com.cmc.dice.domain.brand.dao.BrandRepository;
import com.cmc.dice.domain.brand.domain.Brand;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;
    private final BrandRepository brandRepository;

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

    // 예약 ver2
    public ReservationDtoV2 reserveV2(User user, ReservationRequest reservationRequest) {
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

        return ReservationDtoV2.of(reservation);
    }

    // 공간의 예약된 날짜 확인
    public ReservationAvailableDto getAvailableDates(Long spaceId) {
        List<DateDto> reservedDates = reservationRepository.findReservedDatesBySpaceId(spaceId);
        return ReservationAvailableDto.builder()
                .reservedDates(reservedDates)
                .build();
    }

    //자신의 예약 조회
    public Page<ReservationInfoDto> getMyReservations(User user, String status, int page, int size) {
        Page<Reservation> reservations = reservationRepository.findByUserIdAndStatus(user.getId(), status, PageRequest.of(page, size));
        return reservations.map(this::getReservationInfoDto);
    }

    //자신의 예약 조회 ver2
    public Page<ReservationInfoDtoV2> getMyReservationsV2(User user, String status, String sort, int page, int size) {
        Sort sortOrder = sort.equals("latest")
                ? Sort.by("id").descending()
                : Sort.by("id").ascending();

        Page<Reservation> reservations = reservationRepository.findByUserIdAndStatus(user.getId(), status, PageRequest.of(page, size, sortOrder));

        return reservations.map(this::getReservationInfoDtoV2);
    }

    private ReservationInfoDto getReservationInfoDto(Reservation reservation) {
        Space space = reservation.getSpace();
        int totalPrice = (reservation.getEndDate().getDayOfYear() - reservation.getStartDate().getDayOfYear()) * space.getDiscountPrice();

        return ReservationInfoDto.builder()
                .reservationId(reservation.getId())
                .spaceName(space.getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .city(space.getCity())
                .capacity(space.getCapacity())
                .district(space.getDistrict())
                .size(space.getSize())
                .totalPrice(totalPrice)
                .spaceImage(space.getImageUrls().get(0))
                .build();
    }
    private ReservationInfoDtoV2 getReservationInfoDtoV2(Reservation reservation) {
        Space space = reservation.getSpace();
        LocalDate localDate = reservation.getCreatedAt().toLocalDate();
        int totalPrice = (reservation.getEndDate().getDayOfYear() - reservation.getStartDate().getDayOfYear()) * space.getDiscountPrice();

        return ReservationInfoDtoV2.builder()
                .reservationId(reservation.getId())
                .spaceName(space.getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .city(space.getCity())
                .district(space.getDistrict())
                .reservationDate(localDate == null ? LocalDate.now() : localDate)
                .size(space.getSize())
                .totalPrice(totalPrice)
                .spaceImage(space.getImageUrls().get(0))
                .build();
    }

    private ReservationInfoHostDto getReservationInfoHostDto(Reservation reservation, String brandName) {
        Space space = reservation.getSpace();
        int totalPrice = (reservation.getEndDate().getDayOfYear() - reservation.getStartDate().getDayOfYear()) * space.getDiscountPrice();

        return ReservationInfoHostDto.builder()
                .reservationId(reservation.getId())
                .spaceName(space.getName())
                .brandName(brandName)
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

	public Page<ReservationInfoHostDto> getHostReservations(User user, String status, int page, int size) {
        Page<Reservation> reservations = reservationRepository.findBySpaceAdminIdAndStatus(user.getId(), status, PageRequest.of(page, size));
        return reservations.map(reservation -> {
                    User reservedUser = reservation.getUser();
                    List<Brand> brand = brandRepository.findByAdminId(reservedUser.getId());

                    if (brand.isEmpty()) {
                        return getReservationInfoHostDto(reservation, "");
                    }

                    return getReservationInfoHostDto(reservation, brand.get(0).getName());
                }
        );
	}
}
