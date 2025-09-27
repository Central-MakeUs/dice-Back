package com.cmc.dice.global.fcm.controller;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.fcm.FcmService;
import com.cmc.dice.global.fcm.dto.FcmTokenRequest;
import com.cmc.dice.global.fcm.dto.SendAlarmRequest;
import com.cmc.dice.global.fcm.dto.SendManyAlarmRequest;
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
    public ResponseEntity<?> getToken(
            @CurrentUser User user,
            @Valid @RequestBody FcmTokenRequest tokenReq
    ) {
        fcmService.saveToken(user, tokenReq.fcmToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/one")
    @Operation(summary = "단일 대상 푸시알림", description = """
            # 단일 대상 푸시알림
            - 단일 대상에게 제목과 내용을 담은 푸시알림을 전송합니다.
            - 이때 해당 대상은 꼭 fcmtoken(`POST /api/v1/fcm/token`)을 수행해야합니다.
            
            ## 요청
            - `token`: fcm에서 인증된 토큰값
            - `title`: 알림 제목
            - `body`: 알림 내용
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> sendOneAlarm(
            @Valid @RequestBody SendAlarmRequest request
    ) {
        fcmService.sendOne(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/many")
    @Operation(summary = "단일 대상 푸시알림", description = """
            # 단일 대상 푸시알림
            - 단일 대상에게 제목과 내용을 담은 푸시알림을 전송합니다.
            - 이때 해당 대상은 꼭 fcmtoken(`POST /api/v1/fcm/token`)을 수행해야합니다.
            
            ## 요청
            - `token`: fcm에서 인증된 토큰값
            - `title`: 알림 제목
            - `body`: 알림 내용
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> sendManyAlarm(
            @Valid @RequestBody SendManyAlarmRequest requests
    ) {
        fcmService.sendMany(requests);
        return ResponseEntity.ok().build();
    }

}
