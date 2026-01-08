package com.cmc.dice.domain.space.domain;

import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.time.LocalDate;
import java.util.List;

@Table(name = "analysis_people")
@Entity
@NoArgsConstructor
@Getter
public class SpaceAnalysisPeople extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_people_id")
    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    private String location; // ex: 성수 2동

    private Integer locationCount; // 위치 유동인구수 ex: 120000

    private Integer areaCount; // 지역 유동인구수 ex: 80000

    private Integer nationalCount; // 전국 유동인구수 ex: 30000

    private List<String> targets; // ex: ["20대", "30대", "여성"]

    private List<Integer> ageGroupsCountMan; // ex: [100, 150, 200, 250, 300, 350] -> 10대, 20대, 30대, 40대, 50대, 60대 이상

    private List<Integer> ageGroupsCountWoman; // ex: [100, 150, 200, 250, 300, 350] -> 10대, 20대, 30대, 40대, 50대, 60대 이상

    private List<Integer> dayOfWeekCount; // ex: [120, 130, 140, 150, 160, 170, 180] -> 월, 화, 수, 목, 금, 토, 일

    @Builder
    public SpaceAnalysisPeople(String title, String description, String location,
                               Integer locationCount, Integer areaCount, Integer nationalCount,
                               List<String> targets, List<Integer> ageGroupsCountMan, List<Integer> ageGroupsCountWoman,
                               List<Integer> dayOfWeekCount, LocalDate date) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.location = location;
        this.locationCount = locationCount;
        this.areaCount = areaCount;
        this.nationalCount = nationalCount;
        this.targets = targets;
        this.ageGroupsCountMan = ageGroupsCountMan;
        this.ageGroupsCountWoman = ageGroupsCountWoman;
        this.dayOfWeekCount = dayOfWeekCount;
    }
}
