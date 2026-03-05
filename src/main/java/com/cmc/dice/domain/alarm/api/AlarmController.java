package com.cmc.dice.domain.alarm.api;

import com.cmc.dice.domain.alarm.application.AlarmService;
import com.cmc.dice.domain.alarm.dto.AlarmInfoResponse;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alarm")
@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    @Operation(summary = "알림 조회", description = """
        # 알림 조회
        
        - 유저가 받은 알림을 조회합니다. (fcm 알림이 간 내용에 대해서만 조회할 수 있습니다)
        
        ## 응답
        - `alarmId`: 알림 ID
        - `title`: 알림 제목
        - `content`: 알림 내용
        - `isRead`: 알림 읽음 여부
        """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public List<AlarmInfoResponse> getAlarms(
            @CurrentUser User user
    ) {
        return alarmService.getAlarms(user);
    }

    @PostMapping("/{alarmId}")
    @Operation(summary = "알림 읽기(단일)", description = """
        # 알림 읽기(단일)
        
        - 유저가 받은 알림 하나를 읽음 처리합니다.
        
        """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void readAlarm(
            @CurrentUser User user,
            @PathVariable Long alarmId
    ) {
        alarmService.readAlarm(user, alarmId);
    }

    @PostMapping("")
    @Operation(summary = "알림 읽기(전체)", description = """
        # 알림 읽기(전체)
        
        - 유저가 받은 알림 전체를 읽음 처리합니다.
        
        """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void readAllAlarms(
            @CurrentUser User user
    ) {
        alarmService.readAllAlarms(user);
    }

    @DeleteMapping("/{alarmId}")
    @Operation(summary = "알림 삭제", description = """
        # 알림 삭제
        
        - 유저가 특정 알림을 삭제합니다.
        
        """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void removeAlarm(
            @CurrentUser User user,
            @PathVariable Long alarmId
    ) {
        alarmService.removeAlarm(user, alarmId);
    }
}
