package com.cmc.dice.global.fcm;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class FcmException extends BaseException {

    public FcmException(HttpStatus status, String message) {
        super(status, message);
    }
}
