package com.cmc.dice.domain.space.dto.v2;

import com.cmc.dice.domain.space.domain.SpaceCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpaceRequestV2 {
	@NotBlank
	@Schema(description = "공간 이름", example = "공간 이름")
	private String name;

	@NotBlank
	@Schema(description = "가까운 지하철 정보", example = "공간 설명")
	private NearestSubwayDto nearestSubwayDto;

	// 유동인구 분석을 어떻게할지?

	@NotBlank
	@Schema(description = "이미지 URL 리스트", example = "[\"www.example.com\"]")
	private List<String> imageUrls;

	@NotNull
	@Schema(description = "공간 카테고리", example = "CAFE")
	private SpaceCategory category;

	@NotNull
	@Schema(description = "오픈 시간", example = "09:00")
	private String openingTime;

	@NotNull
	@Schema(description = "마감 시간", example = "18:00")
	private String closingTime;

	@PositiveOrZero
	@Schema(description = "수용 인원", example = "10")
	private int capacity;

	@PositiveOrZero
	@Schema(description = "공간 크기", example = "30")
	private int size;

	@Schema(description = "태그 리스트", example = "[\"카페\"]")
	private List<String> tags;

	@Positive
	@Schema(description = "일일 가격", example = "10000")
	private int pricePerDay;

	@PositiveOrZero
	@Schema(description = "할인율", example = "10")
	private int discountRate;

	@NotBlank
	@Schema(description = "상세 정보", example = "상세 정보")
	private String details;

	@NotNull
	@Schema(description = "위도", example = "37.1234")
	private Double latitude;

	@NotNull
	@Schema(description = "경도", example = "127.1234")
	private Double longitude;

	@NotBlank
	@Schema(description = "도시", example = "서울")
	private String city;

	@NotBlank
	@Schema(description = "구", example = "강남구")
	private String district;

	@NotBlank
	@Schema(description = "주소", example = "강남대로 123")
	private String address;

	@Schema(description = "상세 주소", example = "101동 101호")
	private String detailAddress;

	@NotBlank
	@Schema(description = "연락처", example = "010-1234-5678")
	private String contactNumber;

	@NotBlank
	@Schema(description = "시설 이용 안내")
	private List<FacilityInfoDto> facilityInfos;

	@NotBlank
	@Schema(description = "공지사항",
			example = "[\"채팅 상담을 추천드려요\", \"설치 및 철수는 계약 기간 내 포함이에요\"]")
	private List<String> notices;

	@Schema(description = "활성화 여부", example = "true")
	private Boolean isActivated;
}
