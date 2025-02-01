package com.cmc.dice.domain.message.dao;

import com.cmc.dice.domain.message.domain.MessageRoom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    List<MessageRoom> findByGuestId(Long guestId);

    List<MessageRoom> findByHostId(Long hostId);
}
