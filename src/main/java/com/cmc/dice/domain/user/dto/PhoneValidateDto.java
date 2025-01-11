package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneValidateDto {
    @NotBlank
    @Schema(description = "휴대폰 번호", example = "01012341234")
    private String phone;
}
