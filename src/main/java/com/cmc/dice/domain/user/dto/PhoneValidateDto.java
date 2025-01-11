package com.cmc.dice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneValidateDto {
    @NotBlank
    private String phone;
}
