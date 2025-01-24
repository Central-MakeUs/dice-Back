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
public class GuestInfoDto {
	private String name;

	private String email;

	private String phone;

	private List<SimpleBrandInfoDto> brandList;

	public static GuestInfoDto of(User user, List<SimpleBrandInfoDto> brandList) {
		return new GuestInfoDto(
			user.getName(),
			user.getEmail(),
			user.getPhone(),
			brandList
		);
	}
}