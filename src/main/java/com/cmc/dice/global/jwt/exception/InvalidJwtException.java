package com.cmc.dice.global.jwt.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;


public class InvalidJwtException extends BaseException {
    public InvalidJwtException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}