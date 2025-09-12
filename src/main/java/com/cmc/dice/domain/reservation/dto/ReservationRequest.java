package com.cmc.dice.domain.reservation.dto;

import com.cmc.dice.domain.reservation.domain.Reservation;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @Schema(description = "행사 이름", example = "DICE 스튜디오 2025 F/W 컬렉션 팝업")
    private String eventName;

    @Schema(description = "행사 내용", example = "DICE 스튜디오의 2025 가을/겨울 신규 컬렉션을 가장 먼저 만나보세요. 현장 한정 할인 및 특별 굿즈가 준비되어 있습니다.")
    private String eventContent;

    @Schema(description = "첨부 파일 리스트", example = "[\"https://dice-bucket.s3.ap-northeast-2.amazonaws.com/event/plan.pdf\", \"https://dice-bucket.s3.ap-northeast-2.amazonaws.com/event/design.png\"]")
    private List<String> fileList;

    @Schema(description = "기타 요청 사항", example = "행사 준비를 위해 예약일 전날 저녁 8시 이후부터 공간을 미리 사용할 수 있을지 문의드립니다.")
    private String etcRequest;

    public Reservation toEntity(User user, Space space) {
        return Reservation.builder()
                .user(user)
                .space(space)
                .startDate(startDate)
                .endDate(endDate)
                .eventName(eventName)
                .eventContent(eventContent)
                .fileList(fileList)
                .etcRequest(etcRequest)
                .status("PENDING")
                .build();
    }
}
