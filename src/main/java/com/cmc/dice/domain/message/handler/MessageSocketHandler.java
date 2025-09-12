//package com.cmc.dice.domain.message.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.BinaryMessage;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class MessageSocketHandler extends TextWebSocketHandler {
//    private final ObjectMapper objectMapper;
//
//    // 현재 연결된 session집합
//    private final Set<WebSocketSession> sessions = new HashSet<>();
//
//    // 채팅방id - session의 집합
//    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
//
//    // 소켓 연결
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session); // 세션 저장
//        log.info("{} 소켓 연결 성공", session.getId());
//        String o = (String) session.getAttributes().get("email");
//        System.out.println("connect user = " + o);
//
//        // 세션마다 자신이 아닌 현재 접속한 세션에게 모두 메세지를 보냄.
//        sessions.stream().forEach( s -> {
//                    try {
//                        if(!s.getId().equals(session.getId()))
//                            s.sendMessage(new TextMessage("hello world"));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        );
//    }
//
//    // 데이터(text) 통신
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        /**
//         * session: 연결된 세션아이디 주체
//         * message: dto 내용
//         *
//         * 채팅방에
//         * */
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        MessageRequest messageRequest = objectMapper.readValue(payload, MessageRequest.class);
//        log.info("session: {}", messageRequest.toString());
//
//        log.info("email: {}", session.getAttributes().get("email"));
////        messageDto.setSenderId(session.getId());
//
//        Long chatRoomId = messageRequest.getChatRoomId();
//
//        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
//        // 메모리 상에 채팅방이 있다면 접속자가 있으니 세션 넣어줌.
//        if(!chatRoomSessionMap.containsKey(chatRoomId)){
//            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
//        }
//        chatRoomSessionMap.get(chatRoomId).add(session);
//
//        Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatRoomId);
//        for(WebSocketSession s: chatRoomSessions) {
//            log.info(s.toString());
//        }
//
//        sendMessageToChatRoom(messageRequest, chatRoomSessions);
//    }
//
//    // 데이터(Binary) 통신
//    @Override
//    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//
//    }
//
//    // 소켓 연결 종료
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // TODO Auto-generated method stub
//        log.info("{} 연결 끊김", session.getId());
//        sessions.remove(session);
//
//        // 채팅방 세션 맵에서도 제거
//        Long targetRoomId = null;
//        for (Map.Entry<Long, Set<WebSocketSession>> entry : chatRoomSessionMap.entrySet()) {
//            if (entry.getValue().remove(session)) {
//                targetRoomId = entry.getKey();
//                if (entry.getValue().isEmpty()) {
//                    chatRoomSessionMap.remove(entry.getKey());
//                }
//                break;
//            }
//        }
//
//        session.sendMessage(new TextMessage("WebSocket 연결 종료"));
//    }
//
//    // 웹소켓 통신 에러
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        super.handleTransportError(session, exception);
//    }
//
//    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
//    }
//
//    private void sendMessageToChatRoom(MessageRequest messageRequest, Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, messageRequest));//2
//    }
//
//
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try{
//            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}
