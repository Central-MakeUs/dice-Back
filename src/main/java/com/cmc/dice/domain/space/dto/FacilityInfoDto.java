package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.Facility;
import com.cmc.dice.domain.space.domain.SpaceFacility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FacilityInfoDto (
        @NotBlank
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
        
        public static SpaceFacility toEntity(FacilityInfoDto facilityInfoDto) {
                return SpaceFacility.builder()
                        .facility(Facility.valueOf(facilityInfoDto.key()))
                        .number(facilityInfoDto.number())
                        .build();
        }
}
