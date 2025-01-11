package com.cmc.dice.domain.user.exception;

import org.springframework.http.HttpStatus;
import com.cmc.dice.global.exception.BaseException;

public class PasswordNotMatchException extends BaseException {
    public PasswordNotMatchException() {
        super(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }
}