package com.cmc.dice.domain.message.dao;

import com.cmc.dice.domain.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
