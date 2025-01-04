package com.cmc.dice.domain.user.exception;

import org.springframework.http.HttpStatus;
import com.cmc.dice.global.exception.BaseException;

public class PasswordCheckNotMatchException extends BaseException {
    public PasswordCheckNotMatchException() {
        super(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }
}