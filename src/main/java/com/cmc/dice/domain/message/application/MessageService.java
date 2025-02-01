package com.cmc.dice.domain.message.application;

import com.cmc.dice.domain.message.dao.MessageRepository;
import com.cmc.dice.domain.message.dao.MessageRoomRepository;
import com.cmc.dice.domain.message.domain.MessageRoom;
import com.cmc.dice.domain.message.dto.MessageRoomDto;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
    
    // 메시지 방 목록 조회 (게스트)
    public List<MessageRoomDto> getMessageRoomList(User user) {
        List<MessageRoom> rooms = messageRoomRepository.findByGuestId(user.getId());
        return rooms.stream()
                .map(MessageRoomDto::of)
                .toList();
    }

    // 메시지 방 목록 조회 (호스트)
    public List<MessageRoomDto> getMessageRoomListByHost(User user) {
        List<MessageRoom> rooms = messageRoomRepository.findByHostId(user.getId());
        return rooms.stream()
                .map(MessageRoomDto::of)
                .toList();
    }
}
