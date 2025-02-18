package com.cmc.dice.domain.brand.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundBrandException extends BaseException {
    public NotFoundBrandException() {
        super(HttpStatus.NOT_FOUND, "브랜드를 찾을 수 없습니다.");
    }
}
