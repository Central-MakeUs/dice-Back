package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.domain.SpaceTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpaceSimpleInfoDto {
	@Schema(description = "공간 ID", example = "1")
	private Long id;

	@Schema(description = "공간 이름", example = "공간 이름")
	private String name;

	@Schema(description = "공간 주소", example = "서울시 강남구 테헤란로 123")
	private String address;

	@Schema(description = "시", example = "서울")
	private String city;

	@Schema(description = "구", example = "강남구")
	private String district;

	@Schema(description = "공간 이미지 URL", example = "www.example.com")
	private String imageUrl;

	@Schema(description = "일일 가격", example = "10000")
	private int pricePerDay;

	@Schema(description = "할인율", example = "10")
	private int discountRate;

	@Schema(description = "할인 가격", example = "9000")
	private int discountPrice;

	@Schema(description = "공간 크기", example = "30")
	private int size;

	@Schema(description = "좋아요 수", example = "10")
	private int likeCount;

	@Schema(description = "평수", example = "50")
	private int square;

	@Schema(description = "좋아요 여부", example = "true")
	@Builder.Default
	private Boolean isLiked = false;

	@Schema(description = "활성화 여부", example = "true")
	private Boolean isActivated;

	@Schema(description = "뱃지 내용", example = "20대 여성 방문 상위 10%")
	private String badge;

	public SpaceSimpleInfoDto(Space space, Boolean isLiked){
		this.id = space.getId();
		this.name = space.getName();
		this.address = space.getCity() + " " + space.getDistrict() + " " + space.getAddress();
		this.city = space.getCity();
		this.district = space.getDistrict();

		this.imageUrl = space.getImageUrls().get(0);
		this.pricePerDay = space.getPricePerDay();
		this.discountRate = space.getDiscountRate();
		this.discountPrice = space.getDiscountPrice();
		this.size = space.getSize();
		this.likeCount = space.getLikeCount();
		this.square = (int) Math.round(space.getSize() / 3.305785);
		this.isLiked = isLiked;
		this.badge = space.getBadge();

		this.isActivated = space.isActivated();
	}

	public static SpaceSimpleInfoDto of(Space space) {
		return SpaceSimpleInfoDto.builder()
				.id(space.getId())
				.name(space.getName())
				.address(space.getAddress())
				.city(space.getCity())
				.district(space.getDistrict())

				.imageUrl(space.getImageUrls().get(0))
				.pricePerDay(space.getPricePerDay())
				.discountRate(space.getDiscountRate())
				.discountPrice(space.getDiscountPrice())
				.square((int) Math.round(space.getSize() / 3.305785))
				.size(space.getSize())
				.likeCount(space.getLikeCount())
				.isActivated(space.isActivated())
				.build();
	}
}
