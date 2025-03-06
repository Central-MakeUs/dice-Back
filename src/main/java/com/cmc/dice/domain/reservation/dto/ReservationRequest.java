package com.cmc.dice.domain.reservation.dto;

import com.cmc.dice.domain.reservation.domain.Reservation;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    @Schema(description = "공간 Id", example = "1")
    private Long spaceId;

    @Schema(description = "예약 시작 날짜", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "예약 종료 날짜", example = "2025-01-02")
    private LocalDate endDate;

    @Schema(description = "예약 메시지", example = "안녕하세요")
    private String message;

    public Reservation toEntity(User user, Space space) {
        return Reservation.builder()
                .user(user)
                .space(space)
                .startDate(startDate)
                .endDate(endDate)
                .message(message)
                .build();
    }
}
