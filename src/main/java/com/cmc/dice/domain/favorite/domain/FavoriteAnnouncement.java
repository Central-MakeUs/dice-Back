package com.cmc.dice.domain.favorite.domain;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_announcements")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteAnnouncement {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
}
