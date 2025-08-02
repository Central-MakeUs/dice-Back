package com.cmc.dice.domain.space.dto.v2;

import com.cmc.dice.domain.space.domain.SpaceNearestSubway;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record NearestSubwayDto (
        @Schema(description = "지하철 호선", example = "2호선")
        int lineNumber,
        @Schema(description = "지하철 역 이름", example = "성수역")
        String stationName,
        @Schema(description = "장소에서부터 거리", example = "633")
        int distance
){
        public static NearestSubwayDto of(SpaceNearestSubway nearestSubway) {
                return NearestSubwayDto.builder()
                        .lineNumber(nearestSubway.getLineNumber())
                        .stationName(nearestSubway.getStationName())
                        .distance(nearestSubway.getLineNumber())
                        .build();
        }
}
