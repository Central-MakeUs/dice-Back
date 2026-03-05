package com.cmc.dice.domain.alarm.domain;

import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Entity
@Table(name = "alarms")
@NoArgsConstructor
@Getter
public class Alarm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    private Boolean isRead = false;

    @Builder
    public Alarm(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.isRead = false;
    }

    public void read() {
        this.isRead = true;
    }
}
