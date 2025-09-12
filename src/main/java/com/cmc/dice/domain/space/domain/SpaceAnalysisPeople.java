package com.cmc.dice.domain.space.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "analysis_people")
@Entity
@NoArgsConstructor
@Getter
public class SpaceAnalysisPeople {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_people_id")
    private Long id;

    private String title;

    private String description;



    @Builder
    public SpaceAnalysisPeople(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
