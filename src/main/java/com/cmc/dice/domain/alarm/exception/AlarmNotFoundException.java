package com.cmc.dice.domain.alarm.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AlarmNotFoundException extends BaseException {
    public AlarmNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 알람입니다.");
    }
}
