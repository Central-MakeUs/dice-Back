package com.cmc.dice.global.jwt.filter;

import com.cmc.dice.domain.user.application.UserDetailsServiceImpl;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtWebSocketHandler implements ChannelInterceptor {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 연결 요청시 JWT 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Authorization 헤더 추출
            List<String> authorization = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
            if (authorization != null && !authorization.isEmpty()) {
                String jwt = authorization.get(0).substring(7);
                try {
                    // JWT 토큰 검증
                    String email = tokenService.validateAccessToken(jwt);
                    User user = userRepository.findByEmail(email).orElseThrow();

                    // 사용자 인증 정보 설정
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    log.info("어느시점이게?");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    log.error("An unexpected error occurred: " + e.getMessage());
                    return null;
                }
            } else {
                // 클라이언트 측 타임아웃 처리
                log.error("Authorization header is not found");
                return null;
            }
        }
        return message;
    }
//
//    @Override
//    public Message beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//
//        log.info("dopfha[posfioashpoifadshfhas[");
//        try {
//            String authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION).toString();
//            log.info(authHeaders);
//            if (authHeaders == null || authHeaders.isEmpty()) {
//                throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
//            }
//
//            String token = authHeaders.substring(7); // "Bearer " 이후의 토큰
//            String email = tokenService.validateAccessToken(token);
//
//            log.info("email check: " + email);
//            return true;
//
//        } catch (Exception e) {
//
//        }
//
//        return false;
//    }
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, Exception exception) {
//
//    }
}
