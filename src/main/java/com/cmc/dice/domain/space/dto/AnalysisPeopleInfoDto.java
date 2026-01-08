package com.cmc.dice.domain.space.dto;

import com.cmc.dice.domain.space.domain.SpaceAnalysisPeople;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AnalysisPeopleInfoDto (
        @Schema(description = "유동인구 핵심 분석 제목", example = "전국 20대 여성 유동인구 상위 5%")
        String title,
        @Schema(description = "유동인구 핵심 분석 설명글", example = "주로 사진 촬영 목적 방문이 많아요.")
        String description,
        @Schema(description = "유동인구 날짜", example = "2026-01-01")
        LocalDate date,
        @Schema(description = "지역 위치", example = "성수 2동")
        String location,
        @Schema(description = "위치 유동인구수", example = "120000")
        Integer locationCount,
        @Schema(description = "지역 유동인구수", example = "80000")
        Integer areaCount,
        @Schema(description = "전국 유동인구수", example = "30000")
        Integer nationalCount,
        @Schema(description = "타겟 리스트", example = "[\"20대\", \"30대\", \"여성\"]")
        List<String> targets,
        @Schema(description = "남자 나이대 유동인구분포", example = "[100, 150, 200, 250, 300, 350]")
        List<Integer> ageGroupsCountMan,
        @Schema(description = "여자 나이대 유동인구분포", example = "[100, 150, 200, 250, 300, 350]")
        List<Integer> ageGroupsCountWoman,
        @Schema(description = "요일별 유동인구수", example = "[120, 130, 140, 150, 160, 170, 180]")
        List<Integer> dayOfWeekCount
) {
    public static AnalysisPeopleInfoDto of(SpaceAnalysisPeople entity) {
        return AnalysisPeopleInfoDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .date(entity.getDate())
                .location(entity.getLocation())
                .locationCount(entity.getLocationCount())
                .areaCount(entity.getAreaCount())
                .nationalCount(entity.getNationalCount())
                .targets(entity.getTargets())
                .ageGroupsCountMan(entity.getAgeGroupsCountMan())
                .ageGroupsCountWoman(entity.getAgeGroupsCountWoman())
                .dayOfWeekCount(entity.getDayOfWeekCount())
                .build();
    }
}
