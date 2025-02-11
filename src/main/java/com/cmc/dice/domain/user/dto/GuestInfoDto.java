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
public class GuestInfoDto {
	@Schema(description = "사용자 이름", example = "홍길동")
	private String name;

	@Schema(description = "사용자 이메일", example = "user@email.com")
	private String email;

	@Schema(description = "사용자 휴대폰 번호", example = "01012345678")
	private String phone;

	@Schema(description = "브랜드 리스트")
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