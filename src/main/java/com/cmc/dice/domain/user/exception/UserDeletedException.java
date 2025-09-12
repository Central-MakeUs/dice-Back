package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserDeletedException extends BaseException {
    public UserDeletedException() {
        super(HttpStatus.BAD_REQUEST, "삭제된 사용자입니다.");
    }
}
