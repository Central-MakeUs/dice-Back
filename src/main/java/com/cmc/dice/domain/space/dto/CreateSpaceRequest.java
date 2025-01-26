package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.SpaceCategory;
import com.cmc.dice.domain.space.domain.SpaceTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpaceRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private List<String> imageUrls;

	@NotNull
	private SpaceCategory category;

	@NotNull
	private LocalTime openingTime;

	@NotNull
	private LocalTime closingTime;

	@PositiveOrZero
	private int capacity;

	private List<SpaceTag> tags;

	@Positive
	private int pricePerDay;

	@PositiveOrZero
	private int discountRate;

	@NotBlank
	private String details;

	@NotNull
	private Point location;

	private String websiteUrl;

	@NotBlank
	private String contactNumber;

	@NotBlank
	private String facilityInfo;

	@NotBlank
	private String notice;
}