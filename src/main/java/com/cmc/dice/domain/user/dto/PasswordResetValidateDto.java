package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetValidateDto {
    @NotBlank
    @Schema(description = "인증번호", example = "123456")
    private String code;

    @NotBlank
    @Schema(description = "이메일", example = "user01@email.com")
    private String email;
}
