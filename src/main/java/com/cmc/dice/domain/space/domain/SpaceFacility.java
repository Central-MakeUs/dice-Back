package com.cmc.dice.domain.space.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "facility")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceFacility {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Facility facility;

    private int number;

    @Builder
    public SpaceFacility(Facility facility, int number) {
        this.facility = facility;
        this.number = number;
    }
}
