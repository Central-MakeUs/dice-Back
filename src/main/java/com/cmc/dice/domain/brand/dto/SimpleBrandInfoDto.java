package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.WithBy;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleBrandInfoDto {
	@Schema(description = "브랜드 ID", example = "1")
	private Long id;
	@Schema(description = "브랜드 이름", example = "브랜드 이름")
	private String name;

	@Schema(description = "브랜드 설명", example = "브랜드 설명")
	private String description;

	@Schema(description = "로고 URL", example = "https://example.com/logo.jpg")
	private String logoUrl;
	@Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
	private List<String> imageUrls;

	public static SimpleBrandInfoDto of(Brand brand) {
		return SimpleBrandInfoDto.builder()
				.id(brand.getId())
				.name(brand.getName())
				.description(brand.getDescription())
				.logoUrl(brand.getLogoUrl())
				.imageUrls(brand.getImageUrls())
				.build();
	}
}