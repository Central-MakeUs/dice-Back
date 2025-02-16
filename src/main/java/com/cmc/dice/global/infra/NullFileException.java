package com.cmc.dice.global.infra;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NullFileException extends BaseException {
    public NullFileException() {
        super(HttpStatus.BAD_REQUEST, "파일이 존재하지 않습니다.");
    }
}
