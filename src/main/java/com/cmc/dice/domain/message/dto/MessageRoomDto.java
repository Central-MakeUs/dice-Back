package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.MessageRoom;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "메시지 방 ID", example = "1")
    private Long id;

    @Schema(description = "공간 이름", example = "공간 이름")
    private String spaceName;

    @Schema(description = "공간 이미지", example = "www.example.com/image.jpg")
    private String spaceImage;

    @Schema(description = "마지막 메시지", example = "안녕하세요")
    private String lastMessage;

    @Schema(description = "마지막 메시지 시간", example = "2021-07-01T00:00:00")
    private LocalDateTime lastMessageAt;

    @Schema(description = "안읽은 메시지 수", example = "3")
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