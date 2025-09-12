package com.cmc.dice.domain.user.dto;

import com.cmc.dice.domain.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestV2 {

    @NotBlank
    @Email
    @Schema(description = "사용자 이메일", example = "user01@email.com")
    private String email;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "사용자 이름", example = "name01")
    private String name;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 영문과 숫자를 포함하여 8자 이상이어야 합니다.")
    @Schema(description = "사용자 비밀번호", example = "password123")
    private String password;

    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]{11}$", message = "휴대폰 번호는 숫자 11자리여야 합니다.")
    private String phone;

    @NotBlank
    @Schema(description = "사용자 권한", example = "0")
    private UserRole userRole;
}
