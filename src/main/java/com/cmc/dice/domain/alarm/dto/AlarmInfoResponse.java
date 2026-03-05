package com.cmc.dice.domain.alarm.dto;

import com.cmc.dice.domain.alarm.domain.Alarm;
import lombok.Builder;

@Builder
public record AlarmInfoResponse(
        Long alarmId,
        String title,
        String content,
        Boolean isRead
) {
    public static AlarmInfoResponse of(Alarm alarm) {
        return AlarmInfoResponse.builder()
                .alarmId(alarm.getId())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .isRead(alarm.getIsRead())
                .build();
    }
}
