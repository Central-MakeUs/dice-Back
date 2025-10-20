package com.cmc.dice.domain.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceFilterDtoV2 {
    @Schema(description = "도시", example = "서울")
    private String city;

    @Schema(description = "구", example = "강남구")
    private String district;

    @Schema(description = "성별", example = "female")
    private String gender;

    @Schema(description = "연령대", example = "[20, 30]")
    private List<Integer> ageGroups;

    @Schema(description = "요일", example = "WEDNESDAY",
            allowableValues = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"})
    private DayOfWeek dayOfWeek;

    @Schema(description = "목적", example = "snapshot")
    private String purpose;

    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    @Max(value = 1000000, message = "최대 가격은 1,000,000 이하여야 합니다.")
    @Schema(description = "최소 가격", example = "0")
    private Integer minPrice;

    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    @Max(value = 1000000, message = "최대 가격은 1,000,000 이하여야 합니다.")
    @Schema(description = "최대 가격", example = "1000000")
    private Integer maxPrice;

    @Min(value = 0, message = "최소 크기는 0평 이상이어야 합니다.")
    @Max(value = 150, message = "최대 크기는 150평 이하여야 합니다.")
    @Schema(description = "최소 크기", example = "1")
    private Integer minSize;

    @Min(value = 0, message = "최소 크기는 0평 이상이어야 합니다.")
    @Max(value = 150, message = "최대 크기는 150평 이하여야 합니다.")
    @Schema(description = "최대 크기", example = "10")
    private Integer maxSize;

    @Schema(description = "정렬 기준", example = "price")
    private String sortBy;
}
