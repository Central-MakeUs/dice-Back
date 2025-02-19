package com.cmc.dice.domain.user.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidDataException extends BaseException {
    public InvalidDataException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
