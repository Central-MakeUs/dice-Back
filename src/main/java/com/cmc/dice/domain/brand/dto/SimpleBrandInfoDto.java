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

	@Schema(description = "타겟 성별", example = "[\"male\", \"female\"]")
	private List<String> targetGender;

	@Schema(description = "타겟 나이대", example = "[\"10\", \"20\"]")
	private List<String> targetAgeGroup;


	public static SimpleBrandInfoDto of(Brand brand) {
		return SimpleBrandInfoDto.builder()
				.id(brand.getId())
				.name(brand.getName())
				.description(brand.getDescription())
				.logoUrl(brand.getLogoUrl())
				.imageUrls(brand.getImageUrls())
				.targetAgeGroup(brand.getTargetAgeGroup())
				.targetGender(brand.getTargetGender())
				.build();
	}
}
