package com.cmc.dice.domain.space.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nearest_subway")
@NoArgsConstructor
@Getter
public class NearestSubway {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_subway_id")
    private Long id;

    private int lineNumber;

    private String stationName;

    private int distance;

    @Builder
    public NearestSubway(int lineNumber, String stationName, int distance) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
        this.distance = distance;
    }
}
