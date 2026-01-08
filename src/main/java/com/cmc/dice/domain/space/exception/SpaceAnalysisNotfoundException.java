package com.cmc.dice.domain.space.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SpaceAnalysisNotfoundException extends BaseException {
    public SpaceAnalysisNotfoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 유동인구 분석입니다.");
    }

}
