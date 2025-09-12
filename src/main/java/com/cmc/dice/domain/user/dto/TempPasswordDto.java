package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempPasswordDto {
    @Schema(description = "이메일", example = "user00@email.com")
    private String email;

    @Schema(description = "임시 비밀번호", example = "tempPassword")
    private String tempPassword;
}
