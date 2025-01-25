package com.cmc.dice.domain.space.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceFilterDto {
	private String city;
	private String district;

	private Integer minPrice;
	private Integer maxPrice;

	private Integer maxCapacity;

	private String sortBy;
}