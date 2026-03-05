package com.cmc.dice.domain.alarm.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AlarmUnAuthorizedException extends BaseException {
    public AlarmUnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "알림에 접근할 권한이 없습니다.");
    }
}
