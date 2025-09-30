package com.cmc.dice.domain.reservation.dto;

import com.cmc.dice.domain.reservation.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDtoV2 {
    @Schema(description = "예약 ID", example = "1")
    private Long id;

    @Schema(description = "공간 이름", example = "어서와 카페")
    private String name;

    @Schema(description = "예약 시작 날짜", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "예약 종료 날짜", example = "2025-01-02")
    private LocalDate endDate;

    @Schema(description = "총 금액", example = "100000")
    private int totalPrice;
    public static ReservationDtoV2 of(Reservation reservation) {
        return ReservationDtoV2.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .totalPrice(reservation.getSpace().getDiscountPrice())
                .build();
    }
}
