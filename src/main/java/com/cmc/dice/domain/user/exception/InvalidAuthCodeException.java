package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidAuthCodeException extends BaseException {
    public InvalidAuthCodeException() {
        super(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다.");
    }
}
