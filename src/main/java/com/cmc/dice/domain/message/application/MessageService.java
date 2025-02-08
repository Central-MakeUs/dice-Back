package com.cmc.dice.domain.message.application;

import com.cmc.dice.domain.message.dao.MessageRepository;
import com.cmc.dice.domain.message.dao.MessageRoomRepository;
import com.cmc.dice.domain.message.domain.Message;
import com.cmc.dice.domain.message.domain.MessageRoom;
import com.cmc.dice.domain.message.dto.MessageCreateRequest;
import com.cmc.dice.domain.message.dto.MessageDto;
import com.cmc.dice.domain.message.dto.MessageRoomDto;
import com.cmc.dice.domain.message.dto.MessageSendRequest;
import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

    // 메시지 방 목록 조회 (게스트)
    public List<MessageRoomDto> getMessageRoomListByGuest(User user) {
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

    // 메시지 방 조회
    public Page<MessageDto> getMessageList(User user, Long roomId, Pageable pageable) {
        if (pageable.getPageNumber() == 0) {
            MessageRoom room = messageRoomRepository.findById(roomId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지 방입니다."));

            if (!room.getLastMessageSender().equals(user.getName())){
                room.updateByRead();
                messageRoomRepository.save(room);
            }
        }

        Page<Message> messagePage = messageRepository.findByRoomId(roomId, pageable);
        List<MessageDto> messages = messagePage.stream()
                .map(MessageDto::of)
                .toList();

        return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
    }

    // 메시지 전송
    public MessageDto sendMessage(User user, Long roomId, MessageSendRequest request) {
        Message message = Message.builder()
                .room(messageRoomRepository.getReferenceById(roomId))
                .sender(user)
                .content(request.getContent())
                .type(request.getType())
                .build();

        messageRoomRepository.findById(roomId)
                .ifPresent(room -> {
                    room.updateLastMessage(message);
                    messageRoomRepository.save(room);
                }
        );

        messageRepository.save(message);
        return MessageDto.of(message);
    }

	public MessageRoomDto createMessageRoom(User user, MessageCreateRequest request) {
        MessageRoom room = MessageRoom.builder()
                .guest(user)
                .host(userRepository.getReferenceById(request.getHostId()))
                .space(spaceRepository.getReferenceById(request.getSpaceId()))
                .lastMessage("")
                .lastMessageSender("")
                .lastMessageAt(null)
                .isRead(false)
                .unreadCount(0)
                .build();

        messageRoomRepository.save(room);
        return MessageRoomDto.of(room);
	}
}