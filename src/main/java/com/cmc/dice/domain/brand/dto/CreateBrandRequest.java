package com.cmc.dice.domain.brand.dto;

import com.cmc.dice.domain.brand.domain.Brand;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {

	@Schema(description = "브랜드 이름", example = "나의 브랜드!")
	private String name;

	@Schema(description = "브랜드 설명", example = "제 브랜드는 패션 브랜드입니다.")
	private String description;

	@Schema(description = "로고 URL", example = "www.image.url.com")
	private String logoUrl;

	@Schema(description = "이미지 URLs",
			example = "[\"www.image1.com\", \"www.image2.com\"]")
	private List<String> imageUrls;

	@Schema(description = "브랜드 타겟 성별",
			example = "[\"female\", \"male\"]")
	private List<String> targetGender;

	@Schema(description = "브랜드 타겟 연령대",
			example = "[\"20\", \"30\"]")
	private List<String> targetAgeGroup;
	static public Brand toEntity(User user, CreateBrandRequest request) {
		return Brand.builder()
				.admin(user)
				.name(request.getName())
				.description(request.getDescription())
				.logoUrl(request.getLogoUrl())
				.imageUrls(request.getImageUrls())
				.targetGender(request.getTargetGender())
				.targetAgeGroup(request.getTargetAgeGroup())
				.build();
	}
}
