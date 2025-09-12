package com.cmc.dice.global.fcm.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MessagePushServiceRequest(
        String deviceToken,
        String title,
        String body
) {
    public static MessagePushServiceRequest of(String token, String title, String body) {
        return MessagePushServiceRequest.builder()
                .deviceToken(token)
                .title(title)
                .body(body)
                .build();
    }
}
