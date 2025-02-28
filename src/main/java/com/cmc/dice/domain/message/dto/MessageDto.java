package com.cmc.dice.domain.message.dto;

import com.cmc.dice.domain.message.domain.Message;
import com.cmc.dice.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    @Schema(description = "메시지 ID", example = "1")
    private Long id;

    @Schema(description = "메시지 내용", example = "안녕하세요")
    private String content;

    @Schema(description = "메시지 타입", example = "TEXT")
    private String type;

    @Schema(description = "보낸 사람 이름", example = "티오")
    private String senderName;

    @Schema(description = "보낸 사람 ID", example = "1")
    private Long senderId;

    @Schema(description = "생성 시간", example = "2021-07-01T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "로그인한 사용자의 메시지인지 여부", example = "true")
    private Boolean isLoginUsersMessage;

    public static MessageDto of(Message message, User loginUser) {
        if (Objects.equals(loginUser.getName(), message.getSender().getName())){
            return MessageDto.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .type(message.getType())
                    .senderName(message.getSender().getName())
                    .senderId(message.getSender().getId())
                    .createdAt(message.getCreatedAt())
                    .isLoginUsersMessage(true)
                    .build();
        }

        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .senderName(message.getSender().getName())
                .senderId(message.getSender().getId())
                .createdAt(message.getCreatedAt())
                .isLoginUsersMessage(false)
                .build();
    }

    public static MessageDto of(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .senderName(message.getSender().getName())
                .senderId(message.getSender().getId())
                .createdAt(message.getCreatedAt())
                .isLoginUsersMessage(true)
                .build();
    }
}