package com.cmc.dice.global.fcm.controller;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.fcm.FcmService;
import com.cmc.dice.global.fcm.dto.FcmTokenReq;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
@Tag(name = "Fcm")
public class FcmController {
    private final FcmService fcmService;

    /* FCM Token 서버 저장 API */
    @PostMapping("/token")
    @Operation(summary = "fcm 토큰 저장", description = """
            # fcm 토큰 저장
            fcm토큰을 통해서 사용자의 기기를 구분합니다.
			로그인 혹은 앱 시작 시, fcm토큰을 등록합니다.
			
			## 요청
			- `token`: fcm 토큰
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void getToken(
            @Valid @RequestBody FcmTokenReq tokenReq,
            @CurrentUser User user) {
        fcmService.saveToken(user, tokenReq.getToken());
    }

    @GetMapping("/alarm")
    @Operation(summary = "[테스트] 푸시 알림 요청하기", description = """
            # 푸시알림 테스트
            - 현재 요청이 기기마다 요청이 성공적으로 이루어진다면, 공간예약, 결제 시 적용.
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> pushAlarm(
            @CurrentUser User user
    ) {
        fcmService.testAlarm(user);
        return ResponseEntity.ok().build();
    }

}
