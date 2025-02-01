package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;

    private String content;

    private String type;

    private String senderName;

    private Long senderId;

    private LocalDateTime createdAt;

    public static MessageDto of(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .senderName(message.getSender().getName())
                .senderId(message.getSender().getId())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
