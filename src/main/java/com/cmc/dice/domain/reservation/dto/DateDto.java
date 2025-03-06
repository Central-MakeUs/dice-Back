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
public class DateDto {
    @Schema(description = "예약 시작 날짜", example = "2025-01-01")
    private LocalDate startDate;
    @Schema(description = "예약 종료 날짜", example = "2025-01-02")
    private LocalDate endDate;
}
