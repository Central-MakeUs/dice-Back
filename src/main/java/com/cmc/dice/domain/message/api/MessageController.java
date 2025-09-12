package com.cmc.dice.domain.message.api;

import com.cmc.dice.domain.message.application.MessageService;
import com.cmc.dice.domain.message.application.MessageSocketService;
import com.cmc.dice.domain.message.application.ReportService;
import com.cmc.dice.domain.message.dto.*;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
@Tag(name = "Message")
public class MessageController {
    private final MessageService messageService;
    private final ReportService reportService;

    @GetMapping("/guest-list")
    @Operation(summary = "메시지 목록 조회", description = """
            # 메시지 목록 조회
            - 사용자의 메시지 목록을 조회합니다.
            - 게스트로 로그인한 사용자가 호스트와의 메시지 목록을 조회할 때 사용합니다.
            
            ## 응답
            - `id`: 메시지 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true
    )
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
            호스트로 로그인한 사용자가 게스트와의 메시지 목록을 조회할 때 사용합니다.
            
            ## 응답
            - `id`: 메시지 ID
            - `content`: 메시지 내용
            - `type`: 메시지 타입
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true
    )
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
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true
    )
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
    @ApiResponse(
            responseCode = "200",
            description = "전송 성공",
            useReturnTypeSchema = true
    )
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public MessageDto sendMessage(
            @CurrentUser User user,
            @PathVariable Long roomId,
            @RequestBody MessageSendRequest request) {
        return messageService.sendMessage(user, roomId, request);
    }

    @PostMapping("/create")
    @Operation(summary = "메시지 방 생성", description = """
            # 메시지 방 생성
            - 메시지 방을 생성합니다.
            - 게스트가 호스트와의 메시지 방을 생성할 때 사용합니다.
            
            ## 요청
            - `hostId`: 호스트 ID
            """)
    @ApiResponse(
            responseCode = "200",
            description = "생성 성공",
            useReturnTypeSchema = true
    )
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public MessageRoomDto createMessageRoom(
            @CurrentUser User user,
            @RequestBody MessageCreateRequest request) {
        return messageService.createMessageRoom(user, request);
    }

    // 신고 기능
    @PostMapping("/report")
    @Operation(summary = "메시지 신고", description = """
            # 메시지방 신고
            - 메시지방을 신고합니다.
            - 사용자가 호스트를 신고할 때 사용합니다.
            
            ## 요청
            - `messageRoomId`: 메시지 ID
            - `reason`: 신고 사유
            """)
    @ApiResponse(
            responseCode = "200",
            description = "신고 성공",
            useReturnTypeSchema = true
    )
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void report(
            @CurrentUser User user,
            @RequestBody ReportRequest request) {
        reportService.reportMessage(user, request);
    }
}
