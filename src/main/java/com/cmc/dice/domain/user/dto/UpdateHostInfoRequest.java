package com.cmc.dice.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHostInfoRequest implements Serializable {
	@Schema(description = "사용자 이름", example = "CMC")
	String name;
	@Schema(description = "사용자 이메일", example = "01012345678")
	String phone;
	@Schema(description = "사용자 비밀번호", example = "password123!")
	String password;
	@Schema(description = "은행명", example = "신한은행")
	String bankName;
	@Schema(description = "계좌번호", example = "1234567890")
	String accountNumber;
}