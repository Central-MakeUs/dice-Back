package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.MessageRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRoomDto {
    private Long id;

    private String spaceName;

    private String spaceImage;

    private String lastMessage;

    private LocalDateTime lastMessageAt;

    private Integer unreadCount;

    public static MessageRoomDto of(MessageRoom room) {
        return MessageRoomDto.builder()
                .id(room.getId())
                .spaceName(room.getSpace().getName())
                .spaceImage(room.getSpace().getImageUrls().get(0))
                .lastMessage(room.getLastMessage())
                .lastMessageAt(room.getLastMessageAt())
                .unreadCount(room.getUnreadCount())
                .build();
    }
}
