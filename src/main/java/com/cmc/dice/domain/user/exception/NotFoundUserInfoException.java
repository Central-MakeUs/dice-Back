package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundUserInfoException extends BaseException {
    public NotFoundUserInfoException() {
        super(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다.");
    }
}
