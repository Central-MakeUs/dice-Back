package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	private String name;
	private String description;
	private String logoUrl;
	private List<String> imageUrls;
	private String homepageUrl;

	static public Brand toEntity(CreateBrandRequest request) {
		return Brand.builder()
			.name(request.getName())
			.description(request.getDescription())
			.logoUrl(request.getLogoUrl())
			.imageUrls(request.getImageUrls())
			.homepageUrl(request.getHomepageUrl())
			.build();
	}
}