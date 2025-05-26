package com.cmc.dice.domain.message.api;

import com.cmc.dice.domain.message.application.MessageSocketService;
import com.cmc.dice.domain.message.dto.MessageDto;
import com.cmc.dice.domain.message.dto.MessageSendRequest;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageSocketController {
    private final MessageSocketService messageSocketService;

    // WebSocketConfig에서 setApplicationDestinationPrefixes()를 통해 prefix를 /pub으로 설정 해주었기 때문에,
    // 경로가 한번 더 수정되어 /pub/chat/message로 바뀐다.
    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/sub/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public MessageDto websocketSendMessage(
            @DestinationVariable Long roomId,
            MessageSendRequest message,
            Principal principal) throws IllegalAccessException {
        return messageSocketService.sendMessage(roomId, message);
    }

}
