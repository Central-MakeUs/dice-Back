package com.cmc.dice.domain.message.application;

import com.cmc.dice.domain.message.dao.MessageRepository;
import com.cmc.dice.domain.message.dao.MessageRoomRepository;
import com.cmc.dice.domain.message.domain.Message;
import com.cmc.dice.domain.message.domain.MessageRoom;
import com.cmc.dice.domain.message.dto.MessageDto;
import com.cmc.dice.domain.message.dto.MessageSendRequest;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSocketService {
    private final UserRepository userRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    public MessageDto sendMessage(Long roomId, MessageSendRequest message) throws IllegalAccessException {
        MessageRoom messageRoom = messageRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 채팅방입니다."));
//        User user1 = userRepository.findByEmail(email).orElseThrow();
        Message msg = messageRepository.save(MessageSendRequest.of(message, messageRoom));

        return MessageDto.of(msg);
    }
}
