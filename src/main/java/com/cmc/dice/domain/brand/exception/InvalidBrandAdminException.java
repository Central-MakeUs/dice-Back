package com.cmc.dice.domain.brand.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidBrandAdminException extends BaseException {
    public InvalidBrandAdminException() {
        super(HttpStatus.BAD_REQUEST, "브랜드 관리자가 아닙니다.");
    }
}
