package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.SpaceAnalysisPeople;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AnalysisPersonDto (
        @Schema(description = "유동인구 핵심 분석 제목", example = "전국 20대 여성 유동인구 상위 5%")
        String title,
        @Schema(description = "유동인구 핵심 분석 설명글", example = "주로 사진 촬영 목적 방문이 많아요.")
        String description
) {
        public static AnalysisPersonDto of(SpaceAnalysisPeople analysisPeople) {
                return AnalysisPersonDto.builder()
                        .title(analysisPeople.getTitle())
                        .description(analysisPeople.getDescription())
                        .build();
        }
}
