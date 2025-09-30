package com.cmc.dice.domain.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class ReservationInfoDtoV2 {
    @Schema(description = "예약 ID", example = "1")
    private Long reservationId;

    @Schema(description = "공간 이름", example = "카페")
    private String spaceName;

    @Schema(description = "예약 시작 날짜", example = "2025-01-02")
    private LocalDate startDate;

    @Schema(description = "예약 종료 날짜", example = "2025-01-03")
    private LocalDate endDate;

    @Schema(description = "예약 메시지", example = "안녕하세요")
    private String message;

    @Schema(description = "예약 상태", example = "COMPLETE")
    private String status;

    @Schema(description = "도시", example = "서울")
    private String city;

    @Schema(description = "구", example = "강남구")
    private String district;

    @Schema(description = "수용 인원", example = "10")
    private Integer capacity;

    @Schema(description = "공간 크기", example = "10")
    private Integer size;

    @Schema(description = "총 가격", example = "100000")
    private Integer totalPrice;

    @Schema(description = "공간 이미지", example = "www.example.com/image.jpg")
    private String spaceImage;

    @Schema(description = "예약한 날짜", example = "2025-01-01")
    private LocalDate reservationDate;
}
