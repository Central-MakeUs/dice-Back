package com.cmc.dice.domain.message.application;

import com.cmc.dice.domain.message.dao.MessageRoomRepository;
import com.cmc.dice.domain.message.dao.ReportRepository;
import com.cmc.dice.domain.message.domain.MessageRoom;
import com.cmc.dice.domain.message.dto.ReportRequest;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MessageRoomRepository messageRoomRepository;

    public void reportMessage(User user, ReportRequest reportRequest) {
        MessageRoom messageRoom = messageRoomRepository.findById(reportRequest.getMessageRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지 방입니다."));

        reportRepository.save(reportRequest.toEntity(user, messageRoom.getHost()));
    }
}
