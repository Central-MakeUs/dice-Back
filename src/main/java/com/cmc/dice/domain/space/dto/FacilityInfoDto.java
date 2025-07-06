package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.SpaceFacility;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record FacilityInfoDto (
        @Schema(description = "시설 이름", example = "CCTV")
        String key,
        @Schema(description = "시설 수", example = "1")
        int number
) {
        public static FacilityInfoDto of(SpaceFacility spaceFacility) {
                return FacilityInfoDto.builder()
                        .key(spaceFacility.getFacility().getValue())
                        .number(spaceFacility.getNumber())
                        .build();
        }
}
