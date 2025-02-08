package com.cmc.dice.domain.user.dto;

import com.cmc.dice.domain.brand.dto.SimpleBrandInfoDto;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostInfoDto {
	@Schema(description = "사용자 이름", example = "호스트")
	private String name;

	@Schema(description = "사용자 이메일", example = "enterprize@enterprize.com")
	private String email;

	@Schema(description = "사용자 휴대폰 번호", example = "01012345678")
	private String phone;

	@Schema(description = "은행 이름", example = "신한은행")
	private String bankName;

	@Schema(description = "계좌 번호", example = "1234567890")
	private String accountNumber;

	public static HostInfoDto of(User user) {
		return new HostInfoDto(
			user.getName(),
			user.getEmail(),
			user.getPhone(),
			user.getBankName(),
			user.getAccountNumber()
		);
	}
}