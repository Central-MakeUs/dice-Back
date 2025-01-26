package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceInfoDto {
	private Long id;

	private String name; // 공간 이름
	private String description; // 공간 한줄 소개
	private List<String> imageUrls;
	private SpaceCategory category; // 공간 카테고리 (예: 갤러리, 카페 등)

	private LocalTime openingTime; // 공간 운영 시작 시간
	private LocalTime closingTime; // 공간 운영 마감 시간

	private int capacity; // 수용 인원

	private List<Tag> tags;

	private int pricePerDay; // 1일 대여 비용

	private int discountRate; // 할인율 (%)

	// 공간 상세 소개 작성
	@Lob
	private String details; // 공간 상세 소개

	// 공간 위치 정보
	private Double latitude;
	private Double longitude;

	// 주소
	private String city;
	private String district;
	private String address;

	private String websiteUrl; // 웹사이트 URL
	private String contactNumber; // 연락처

	private String facilityInfo; // 시설 이용 안내
	private String notice; // 공지사항

	private int likeCount = 0; // 좋아요 수

	public static SpaceInfoDto of(Space space) {
		List<Tag> tags = new ArrayList<>();
		space.getTags().forEach(spaceTag -> tags.add(spaceTag.getTag()));

		return new SpaceInfoDto(
				space.getId(),
				space.getName(),
				space.getDescription(),
				space.getImageUrls(),
				space.getCategory(),
				space.getOpeningTime(),
				space.getClosingTime(),
				space.getCapacity(),
				tags,
				space.getPricePerDay(),
				space.getDiscountRate(),
				space.getDetails(),
				space.getLocation().getX(),
				space.getLocation().getY(),
				space.getCity(),
				space.getDistrict(),
				space.getAddress(),
				space.getWebsiteUrl(),
				space.getContactNumber(),
				space.getFacilityInfo(),
				space.getNotice(),
				space.getLikeCount()
		);
	}
}