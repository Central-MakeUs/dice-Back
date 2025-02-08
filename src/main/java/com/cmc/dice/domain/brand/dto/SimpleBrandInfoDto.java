package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBrandInfoDto {
	@Schema(description = "브랜드 ID", example = "1")
	private Long id;
	@Schema(description = "브랜드 이름", example = "브랜드 이름")
	private String name;
	@Schema(description = "로고 URL", example = "https://example.com/logo.jpg")
	private String logoUrl;

	public static SimpleBrandInfoDto of(Brand brand) {
		return new SimpleBrandInfoDto(brand.getId(), brand.getName(), brand.getLogoUrl());
	}
}