package com.cmc.dice.domain.message.api;

import com.cmc.dice.domain.message.application.MessageService;
import com.cmc.dice.domain.message.dto.MessageDto;
import com.cmc.dice.domain.message.dto.MessageRoomDto;
import com.cmc.dice.domain.message.dto.MessageSendRequest;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
@Tag(name = "Message")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/guest-list")
    @Operation(summary = "메시지 목록 조회", description = """
            # 메시지 목록 조회
            사용자의 메시지 목록을 조회합니다.
            
            ## 응답
            - `id`: 메시지 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public List<MessageRoomDto> getMessageListByGuest(
            @CurrentUser User user) {
        return messageService.getMessageRoomListByGuest(user);
    }

    @GetMapping("/host-list")
    @Operation(summary = "메시지 목록 조회", description = """
            # 메시지 목록 조회
            사용자의 메시지 목록을 조회합니다.
            
            ## 응답
            - `id`: 메시지 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public List<MessageRoomDto> getMessageListByHost(
            @CurrentUser User user) {
        return messageService.getMessageRoomListByHost(user);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "메시지 상세 조회", description = """
            # 메시지 상세 조회
            메시지 상세 정보를 조회합니다.
            
            ## 요청
            - `roomId`: 메시지 방 ID
            
            ## 응답
            - `id`: 메시지 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public Page<MessageDto> getMessageList(
            @CurrentUser User user,
            @PathVariable Long roomId,
			@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return messageService.getMessageList(user, roomId, PageRequest.of(page, size));
    }

    @PostMapping("/{roomId}")
    @Operation(summary = "메시지 전송", description = """
            # 메시지 전송
            메시지를 전송합니다.
            
            ## 요청
            - `roomId`: 메시지 방 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public MessageDto sendMessage(
            @CurrentUser User user,
            @PathVariable Long roomId,
            @RequestBody MessageSendRequest request) {
        return messageService.sendMessage(user, roomId, request);
    }
}
