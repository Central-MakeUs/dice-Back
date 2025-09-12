package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.Message;
import com.cmc.dice.domain.message.domain.MessageRoom;
import com.cmc.dice.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    private String content;
    private String type;
    private LocalDate createdAt;

    public static Message of(MessageSendRequest message, MessageRoom messageRoom) {
        return Message.builder()
                .room(messageRoom)
//                .sender(user)
                .content(message.getContent())
                .type(message.getType())
                .build();
    }
}
