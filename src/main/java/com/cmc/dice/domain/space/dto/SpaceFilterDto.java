package com.cmc.dice.domain.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceFilterDto {
	@Schema(description = "도시", example = "서울")
	private String city;

	@Schema(description = "구", example = "강남구")
	private String district;

	@Schema(description = "최소 가격", example = "10000")
	private Integer minPrice;

	@Schema(description = "최대 가격", example = "1000000")
	private Integer maxPrice;

	@Schema(description = "최대 수용 인원", example = "10")
	private Integer maxCapacity;

	@Schema(description = "정렬 기준", example = "price")
	private String sortBy;
}