package com.cmc.dice.global.config;

//import com.cmc.dice.domain.message.handler.MessageSocketHandler;
import com.cmc.dice.global.jwt.filter.JwtWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

@EnableWebSocketMessageBroker
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final MessageSocketHandler messageSocketHandler;
    private final JwtWebSocketHandler jwtWebSocketHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                // 테스트할때만 모든 요청 허용. 나중에는 도메인달고 허용시키기.
                .setAllowedOriginPatterns("*")
                // client가 sockjs로 개발되어 있을 때만 필요, client가 java면 필요없음
//                .addInterceptors(jwtWebSocketHandler)
//                .withSockJS()
                ;
    }

    /*한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //sub으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
        registry.enableSimpleBroker("/sub");
        // pub로 시작되는 메시지는 message-handling methods로 라우팅된다.
        registry.setApplicationDestinationPrefixes("/pub");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtWebSocketHandler);
    }
}

