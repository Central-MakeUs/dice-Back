package com.cmc.dice.global.fcm;

import com.cmc.dice.global.fcm.dto.NotificationMulticastRequest;
import com.cmc.dice.global.fcm.dto.NotificationRequest;
import com.cmc.dice.global.fcm.dto.NotificationSingleRequest;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMessage(final NotificationSingleRequest request) {
        try {
            val message = request.buildMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendAsync(message);
        } catch (RuntimeException exception) {
            throw new FcmException(HttpStatus.FORBIDDEN, exception.getMessage());
        }
    }

    public void sendMessages(final NotificationMulticastRequest request) {
        try {
            val messages = request.buildSendMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendMulticastAsync(messages);
        } catch (RuntimeException exception) {
            throw new FcmException(HttpStatus.FORBIDDEN, exception.getMessage());
        }
    }

    private ApnsConfig getApnsConfig(NotificationRequest request) {
        val alert = ApsAlert.builder().setTitle(request.title()).setBody(request.body()).build();
        val aps = Aps.builder().setAlert(alert).setSound("default").build();
        return ApnsConfig.builder().setAps(aps).build();
    }
}
