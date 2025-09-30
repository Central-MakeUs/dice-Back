package com.cmc.dice.global.fcm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SendAlarmRequest (
        @NotBlank
        String fcmToken,

        String title,

        String body
) {
}
