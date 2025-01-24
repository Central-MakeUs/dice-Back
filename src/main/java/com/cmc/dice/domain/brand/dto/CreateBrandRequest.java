package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	private String name;
	private String description;
	private String logoUrl;
	private String homepageUrl;

	static public Brand toEntity(CreateBrandRequest request) {
		return Brand.builder()
			.name(request.getName())
			.description(request.getDescription())
			.logoUrl(request.getLogoUrl())
			.homepageUrl(request.getHomepageUrl())
			.build();
	}
}