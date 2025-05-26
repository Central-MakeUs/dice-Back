package com.cmc.dice.global.fcm;

import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.fcm.dto.MessagePushRequest;
import com.cmc.dice.global.fcm.dto.MessagePushServiceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Value("${fcm.file-path}")
    private String FIREBASE_CONFIG_PATH;

    @Value("${fcm.url}")
    private String FIREBASE_API_URI;

    @Value("${fcm.google_api}")
    private String GOOGLE_API_URI;

    @Transactional(readOnly = true)
    public void saveToken(User user, String token) {
        user.updateFcmToken(token);
    }

    public void sendAlarm(User user) {
        try {
            Message message = Message.builder()
                    .setToken(user.getFcmToken())
                    .setWebpushConfig(WebpushConfig.builder()
                            .putHeader("ttl", "300")
                            .setNotification(new WebpushNotification("Gappa", "content"))
                            .build())
                    .build();
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        }
        catch (Exception e) {

        }
    }

    public void testAlarm(User user) {
        MessagePushServiceRequest request =
                MessagePushServiceRequest.of(user.getFcmToken(), "푸시알림 제목", "푸시알림 내용");
        val restClient = RestClient.create();
        restClient.post()
                .uri(FIREBASE_API_URI) // 요청할 FCM의 REST API
                .contentType(APPLICATION_JSON)
                .body(makeMessage(request))
                .header(AUTHORIZATION, "Bearer " + getAccessToken())
                .header(ACCEPT, "application/json; UTF-8")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (fcmRequest, fcmResponse) -> {
                    throw new FcmException((HttpStatus) fcmResponse.getStatusCode(), "요청이 잘못되었습니다.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (fcmRequest, fcmResponse) -> {
                    throw new FcmException((HttpStatus) fcmResponse.getStatusCode(), "url이 잘못되었습니다.");
                })
                .toBodilessEntity();
    }

    private String makeMessage(MessagePushServiceRequest request) { // 푸시알림 요청 값 생성
        try {
            val message = MessagePushRequest.of(request);
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException exception) {
            throw new FcmException(HttpStatus.BAD_REQUEST, "요청 방식이 잘못되었습니다.");
        }
    }

    private String getAccessToken() { // FCM의 API에 요청하기 위한 Access Token 발급
        try {
            val googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                    .createScoped(List.of(GOOGLE_API_URI));
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException exception) {
            throw new FcmException(HttpStatus.BAD_REQUEST, "요청 방식이 잘못되었습니다.");
        }
    }
}
