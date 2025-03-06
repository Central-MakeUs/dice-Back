package com.cmc.dice.domain.message.domain;


import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "message_rooms")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private User guest;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Message> messages;

    private String lastMessage;
    private String lastMessageSender;
    private LocalDateTime lastMessageAt;
    private boolean isRead;
    private int unreadCount;

    public void updateByRead() {
        this.isRead = true;
        this.unreadCount = 0;
    }

    public void updateLastMessage(Message message) {
        this.lastMessage = message.getContent();
        this.lastMessageSender = message.getSender().getName();
        this.lastMessageAt = LocalDateTime.now();
        this.isRead = false;
        this.unreadCount++;
    }
}
