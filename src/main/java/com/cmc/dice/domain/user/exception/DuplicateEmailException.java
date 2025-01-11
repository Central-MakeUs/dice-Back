package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException() {
        super(HttpStatus.CONFLICT, "입력한 이메일이 이미 존재합니다.");
    }
}
