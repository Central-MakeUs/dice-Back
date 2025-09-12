package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AuthCodeExpiredException extends BaseException {
    public AuthCodeExpiredException() {
        super(HttpStatus.BAD_REQUEST, "인증번호가 만료되었습니다.");
    }
}
