package com.cmc.dice.global.fcm;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.fcm.dto.SendAlarmRequest;
import com.cmc.dice.global.fcm.dto.SendManyAlarmRequest;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FcmService {
    @Transactional
    public void saveToken(User user, String token) {
        user.updateFcmToken(token);
    }

    public void sendOne(SendAlarmRequest request) {
        Message message = Message.builder()
                .setToken(request.fcmToken())
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.body())
                        .build())
                .build();
        try {
            log.info(FirebaseMessaging.getInstance().send(message));
        } catch (FirebaseMessagingException e) {
            throw new FirebaseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void sendMany(SendManyAlarmRequest request) {
        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(request.fcmTokens())
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.body())
                        .build())
                .build();
        try {
            FirebaseMessaging.getInstance().sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            throw new FirebaseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
