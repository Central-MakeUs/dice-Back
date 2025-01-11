package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidateDto {
    @NotBlank
    @Schema(description = "이메일", example = "admin@admin.com")
    private String email;
}
