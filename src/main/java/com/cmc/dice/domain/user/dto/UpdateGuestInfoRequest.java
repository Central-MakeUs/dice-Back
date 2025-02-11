package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGuestInfoRequest implements Serializable {
	@Schema(description = "사용자 이름", example = "홍길동")
	String name;

	@Schema(description = "사용자 이메일", example = "user01@email.com")
	String email;

	@Schema(description = "사용자 휴대폰 번호", example = "01012345678")
	String phone;
}