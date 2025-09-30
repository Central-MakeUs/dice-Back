package com.cmc.dice.global.fcm.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SendManyAlarmRequest (
        @NotBlank
        List<String> fcmTokens,

        String title,

        String body
) {
}
