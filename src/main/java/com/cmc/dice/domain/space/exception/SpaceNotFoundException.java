package com.cmc.dice.domain.space.exception;

import com.cmc.dice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SpaceNotFoundException extends BaseException {
	public SpaceNotFoundException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 공간입니다.");
	}
}