package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBrandInfoDto {
	private Long id;
	private String name;
	private String logoUrl;

	public static SimpleBrandInfoDto of(Brand brand) {
		return new SimpleBrandInfoDto(brand.getId(), brand.getName(), brand.getLogoUrl());
	}
}