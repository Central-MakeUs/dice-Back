package com.cmc.dice.global.fcm;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class FirebaseException extends BaseException {
    public FirebaseException(HttpStatus status, String message) {
        super(status, message);
    }
}
