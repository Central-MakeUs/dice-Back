package com.cmc.dice.domain.reservation.dto;

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
public class ReservationInfoDto {
    @Schema(description = "예약 ID", example = "1")
    private Long reservationId;

    @Schema(description = "공간 이름", example = "카페")
    private String spaceName;

    @Schema(description = "예약 시작 날짜", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "예약 종료 날짜", example = "2025-01-02")
    private LocalDate endDate;

    @Schema(description = "예약 메시지", example = "안녕하세요")
    private String message;
}
