package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.Space;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceSimpleInfoDto {
	private Long id;
	private String name;
	private String address;
	private String imageUrl;

	private int pricePerDay;
	private int discountRate;
	private int discountPrice;
	private int capacity;

	private int likeCount;

	public static SpaceSimpleInfoDto of(Space space) {
		return new SpaceSimpleInfoDto(
				space.getId(),
				space.getName(),
				space.getCity() + " " + space.getDistrict() + " " + space.getAddress(),
				space.getImageUrls().get(0),
				space.getPricePerDay(),
				space.getDiscountRate(),
				space.getDiscountPrice(),
				space.getCapacity(),
				space.getLikeCount()
		);
	}
}