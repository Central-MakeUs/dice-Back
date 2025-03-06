package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.Report;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @Schema(description = "신고할 메시지 방 ID", example = "1")
    private Long messageRoomId;
    @Schema(description = "신고 사유", example = "욕설")
    private String reason;

    public Report toEntity(User user, User reportedUser) {
        return Report.builder()
                .reporter(user)
                .reportedUser(reportedUser)
                .reason(reason)
                .build();
    }
}
