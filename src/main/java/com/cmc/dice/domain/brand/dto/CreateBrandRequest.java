package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import com.cmc.dice.domain.user.domain.User;
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

	static public Brand toEntity(User user, CreateBrandRequest request) {
		return Brand.builder()
			.admin(user)
			.name(request.getName())
			.description(request.getDescription())
			.logoUrl(request.getLogoUrl())
			.imageUrls(request.getImageUrls())
			.homepageUrl(request.getHomepageUrl())
			.build();
	}
}