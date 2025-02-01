package com.cmc.dice.domain.message.domain;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private MessageRoom room;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String content;

    private String type;
}