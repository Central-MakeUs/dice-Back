package com.cmc.dice.domain.space.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SpaceNotOwnerException extends BaseException {
	public SpaceNotOwnerException() {
		super(HttpStatus.FORBIDDEN, "해당 공간의 소유자가 아닙니다.");
	}
}