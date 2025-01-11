package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicatePhoneException extends BaseException {
    public DuplicatePhoneException() {
        super(HttpStatus.CONFLICT, "입력한 휴대폰 번호가 이미 존재합니다.");
    }
}
