package com.cmc.dice.domain.space.domain;

import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "analysis_people")
@Entity
@NoArgsConstructor
@Getter
public class SpaceAnalysisPeople extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_people_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    private LocalDate date;;

    private Integer totalPopulation;

    private String ageGenderStats; // json

    private String dayStats; // json

    private String topGender;

    private Integer topAgeGroup;

    private String topDayOfWeek;

    @Builder
    public SpaceAnalysisPeople(LocalDate date, Integer totalPopulation, String ageGenderStats,
                               String dayStats, String topGender, Integer topAgeGroup,
                               String topDayOfWeek, Space space) {
        this.date = date;
        this.totalPopulation = totalPopulation;
        this.ageGenderStats = ageGenderStats;
        this.dayStats = dayStats;
        this.topGender = topGender;
        this.topAgeGroup = topAgeGroup;
        this.topDayOfWeek = topDayOfWeek;
        this.space = space;
    }
}
