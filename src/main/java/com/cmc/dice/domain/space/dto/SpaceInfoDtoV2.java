package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.domain.SpaceCategory;
import com.cmc.dice.domain.space.domain.SpaceTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpaceInfoDtoV2 {
	@Schema(description = "공간 ID", example = "1")
	private Long id;

	@Schema(description = "공간 이름", example = "공간 이름")
	private String name; // 공간 이름

	private NearestSubwayDto nearestSubway;

	private AnalysisPersonDto analysis;

	@Schema(description = "이미지 URL 리스트",
			example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
	private List<String> imageUrls;

	@Schema(description = "공간 카테고리", example = "갤러리")
	private SpaceCategory category; // 공간 카테고리 (예: 갤러리, 카페 등)

	@Schema(description = "공간 운영 시작 시간", example = "09:00:00")
	private LocalTime openingTime; // 공간 운영 시작 시간

	@Schema(description = "공간 운영 마감 시간", example = "18:00:00")
	private LocalTime closingTime; // 공간 운영 마감 시간

	@Schema(description = "수용 인원", example = "100")
	private int capacity; // 수용 인원

	@Schema(description = "공간 크기", example = "30")
	private int size; //공간 크기

	@Schema(description = "태그 리스트", example = "[\"태그1\", \"태그2\"]")
	private List<String> tags;

	@Schema(description = "1일 대여 비용", example = "100000")
	private int pricePerDay; // 1일 대여 비용

	@Schema(description = "할인율 (%)", example = "10")
	private int discountRate; // 할인율 (%)

	@Schema(description = "할인 가격", example = "9000")
	private int discountPrice;
	// 공간 상세 소개 작성
	@Lob
	@Schema(description = "공간 상세 소개", example = "공간 상세 소개")
	private String details; // 공간 상세 소개

	// 공간 위치 정보
	@Schema(description = "위도", example = "37.123456")
	private Double latitude;
	@Schema(description = "경도", example = "127.123456")
	private Double longitude;

	// 주소
	@Schema(description = "도시", example = "서울")
	private String city;
	@Schema(description = "구", example = "강남구")
	private String district;
	@Schema(description = "주소", example = "강남대로 123")
	private String address;
	@Schema(description = "상세 주소", example = "123동 123호")
	private String detailAddress;

	@Schema(description = "연락처", example = "010-1234-5678")
	private String contactNumber; // 연락처

	@Lob
	@Schema(description = "공지사항",
			example = "[\"채팅 상담을 추천드려요\", \"설치 및 철수는 계약 기간 내 포함이에요\"]")
	private List<String> notices; // 공지사항

	@Schema(description = "좋아요 수", example = "10")
	private int likeCount = 0; // 좋아요 수

	@Builder.Default
	@Schema(description = "좋아요 여부", example = "false")
	private Boolean isLiked = false;

	@Schema(description = "메시지방 ID", example = "1")
	private Long messageRoomId;

	@Schema(description = "활성화 여부", example = "true")
	private Boolean isActivated;

	private List<FacilityInfoDto> facilityInfos;

	public SpaceInfoDtoV2(Space space, Boolean isLiked, Object messageRoomId) {
		this.id = space.getId();
		this.name = space.getName();
		this.nearestSubway = NearestSubwayDto.of(space.getNearestSubway());
		this.analysis = AnalysisPersonDto.of(space.getAnalysisPeople());

		this.imageUrls = space.getImageUrls();
		this.category = space.getCategory();
		this.openingTime = space.getOpeningTime();
		this.closingTime = space.getClosingTime();
		this.capacity = space.getCapacity();
		this.size = space.getSize();

		List<String> tags = new ArrayList<>();
		for (SpaceTag spaceTag : space.getTags()) {
			tags.add(spaceTag.getTag().getName());
		}
		this.tags = tags;

		this.pricePerDay = space.getPricePerDay();
		this.discountRate = space.getDiscountRate();
		this.details = space.getDetails();
		this.latitude = space.getLocation().getY();
		this.longitude = space.getLocation().getX();
		this.city = space.getCity();
		this.district = space.getDistrict();
		this.address = space.getAddress();
		this.detailAddress = space.getDetailAddress();
		this.contactNumber = space.getContactNumber();
		this.facilityInfos = space.getFacilityInfos()
				.stream()
				.map(facilityInfo -> FacilityInfoDto.of(facilityInfo)).toList();
		this.notices = space.getNotices();
		this.likeCount = space.getLikeCount();

		this.isLiked = isLiked != null ? isLiked : false;
		this.messageRoomId = (Long) messageRoomId;

		this.isActivated = space.isActivated();
	}

	public static SpaceInfoDtoV2 of(Space space) {
		List<String> tags = new ArrayList<>();
		for (SpaceTag spaceTag : space.getTags()) {
			if (spaceTag.getTag() == null) {
				continue;
			}
			tags.add(spaceTag.getTag().getName());
		}

		return SpaceInfoDtoV2.builder()
				.id(space.getId())
				.name(space.getName())
				.imageUrls(space.getImageUrls())
				.category(space.getCategory())
				.openingTime(space.getOpeningTime())
				.closingTime(space.getClosingTime())
				.capacity(space.getCapacity())
				.size(space.getSize())
				.tags(tags)
				.pricePerDay(space.getPricePerDay())
				.discountRate(space.getDiscountRate())
				.details(space.getDetails())
				.latitude(space.getLocation().getY())
				.longitude(space.getLocation().getX())
				.city(space.getCity())
				.district(space.getDistrict())
				.address(space.getAddress())
				.contactNumber(space.getContactNumber())
				.facilityInfos(space.getFacilityInfos()
						.stream()
						.map(FacilityInfoDto::of)
						.toList())
				.notices(space.getNotices())
				.likeCount(space.getLikeCount())
				.isActivated(space.isActivated())
				.build();
	}
}
