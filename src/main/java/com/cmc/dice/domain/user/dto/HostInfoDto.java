package com.cmc.dice.domain.user.dto;

import com.cmc.dice.domain.brand.dto.SimpleBrandInfoDto;
import com.cmc.dice.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostInfoDto {
	private String name;

	private String email;

	private String phone;

	private String bankName;

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