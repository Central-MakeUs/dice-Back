package com.cmc.dice.domain.user.dto;

import com.cmc.dice.global.jwt.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    UserAuthInfoDto user;
    TokenDto token;
}