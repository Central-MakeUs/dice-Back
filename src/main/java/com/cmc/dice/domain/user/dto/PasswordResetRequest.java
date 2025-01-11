package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    @NotBlank
    @Schema(description = "이메일", example = "admin@admin.com")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "password123!")
    private String password;

    @NotBlank
    @Schema(description = "새 비밀번호", example = "newPassword123!")
    private String newPassword;

    @NotBlank
    @Schema(description = "토큰", example = "d3d3LGkvdjEvYXV0aC9wYXNz")
    private String token;
}
