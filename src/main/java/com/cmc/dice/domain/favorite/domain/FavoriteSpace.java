package com.cmc.dice.domain.favorite.domain;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_spaces")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteSpace {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
