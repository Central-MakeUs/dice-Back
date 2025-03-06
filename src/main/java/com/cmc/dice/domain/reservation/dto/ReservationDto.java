package com.cmc.dice.domain.reservation.dto;

import com.cmc.dice.domain.reservation.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReservationDto {
    @Schema(description = "예약 ID", example = "1")
    private Long id;

    @Schema(description = "예약자 이름", example = "홍길동")
    private String name;

    @Schema(description = "예약자 이메일", example = "user01@email.com")
    private String email;

    @Schema(description = "예약 시작 날짜", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "예약 종료 날짜", example = "2025-01-02")
    private LocalDate endDate;

    @Schema(description = "예약 메시지", example = "안녕하세요")
    private String message;

    public static ReservationDto of(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .email(reservation.getUser().getEmail())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .message(reservation.getMessage())
                .build();
    }
}
