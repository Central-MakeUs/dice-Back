package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {
    @NotBlank
    @Schema(description = "기존 비밀번호", example = "password123!")
    private String password;

    @NotBlank
    @Schema(description = "새 비밀번호", example = "newPassword123!")
    private String newPassword;
}
