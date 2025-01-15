package com.cmc.dice.domain.space.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "space_tags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceTag {
    @Id
    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
