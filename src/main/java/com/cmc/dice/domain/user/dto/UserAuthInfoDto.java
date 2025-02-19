package com.cmc.dice.domain.user.dto;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthInfoDto {
    @Schema(description = "사용자 이메일", example = "user01@email.com")
    private String email;
    @Schema(description = "사용자 이름", example = "name01")
    private String name;
    @Schema(description = "권한", example = "ROLE_USER")
    private UserRole userRole;

    public static UserAuthInfoDto fromEntity(User user) {
        return UserAuthInfoDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userRole(user.getUserRole())
                .build();
    }
}