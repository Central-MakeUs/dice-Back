package com.cmc.dice.domain.reservation.api;

import com.cmc.dice.domain.message.application.MessageService;
import com.cmc.dice.domain.message.application.ReportService;
import com.cmc.dice.domain.message.dto.*;
import com.cmc.dice.domain.reservation.application.ReservationService;
import com.cmc.dice.domain.reservation.dto.*;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Deprecated
    @GetMapping("/v1/reservation/list")
    @Operation(summary = "예약 목록 조회", description = """
            # 예약 목록 조회
            - 사용자의 예약 목록을 조회합니다.
            - 사용자가 자신의 예약 목록을 조회할 때 사용합니다.
            - `status`에 따라 조회할 수 있습니다. (PENDING, ACCEPT, DECLINE, CANCEL)
            
            ## 응답
            - `id`: 예약 ID
            - `name`: 예약자 이름
            - `email`: 예약자 이메일
            - `startDate`: 예약 시작 날짜
            - `endDate`: 예약 종료 날짜
            - `message`: 예약 메시지
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public Page<ReservationInfoDto> getReservationList(
            @CurrentUser User user,
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reservationService.getMyReservations(user, status, page, size);
    }

    @GetMapping("/v2/reservation/list")
    @Operation(summary = "예약 목록 조회 ver2", description = """
            # 예약 목록 조회 ver2
            - 사용자의 예약 목록을 조회합니다.
            - 사용자가 자신의 예약 목록을 조회할 때 사용합니다.
            - `status`에 따라 조회할 수 있습니다. (PENDING, ACCEPT, DECLINE, CANCEL)
            - `sort`에 따라 조회할 수 있습니다. (latest(default), oldest)
            
            ## 응답
            - `id`: 예약 ID
            - `name`: 예약자 이름
            - `email`: 예약자 이메일
            - `startDate`: 예약 시작 날짜
            - `endDate`: 예약 종료 날짜
            - `message`: 예약 메시지
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public Page<ReservationInfoDtoV2> getReservationListV2(
            @CurrentUser User user,
            @RequestParam String status,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reservationService.getMyReservationsV2(user, status, sort, page, size);
    }

    // 호스트의 자신의 공간 예약 조회
    @GetMapping("/v1/reservation/host-list")
    @Operation(summary = "호스트 예약 목록 조회", description = """
            # 호스트 예약 목록 조회
            - 호스트의 예약 목록을 조회합니다.
            - 호스트가 자신의 예약 목록을 조회할 때 사용합니다.
            - `status`에 따라 조회할 수 있습니다. (PENDING, ACCEPT, DECLINE, CANCEL)
            
            ## 응답
            - `id`: 예약 ID
            - `name`: 예약자 이름
            - `email`: 예약자 이메일
            - `startDate`: 예약 시작 날짜
            - `endDate`: 예약 종료 날짜
            - `message`: 예약 메시지
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public Page<ReservationInfoHostDto> getHostReservationList(
            @CurrentUser User user,
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reservationService.getHostReservations(user, status, page, size);
    }


    @Deprecated
    @PostMapping("/v1/reservation/reserve")
    @Operation(summary = "예약", description = """
            # 예약
            - 공간을 예약합니다.
            - 사용자가 공간을 예약할 때 사용합니다.
            
            ## 요청
            - `spaceId`: 공간 ID
            - `startDate`: 예약 시작 날짜
            - `endDate`: 예약 종료 날짜
            - `message`: 예약 메시지
            """)
    @ApiResponse(
            responseCode = "200",
            description = "예약 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ReservationDto reserve(
            @CurrentUser User user,
            @RequestBody ReservationRequest request) {
        return reservationService.reserve(user, request);
    }

    @PostMapping("/v2/reservation/reserve")
    @Operation(summary = "예약 ver2", description = """
            # 예약 ver2
            - 공간을 예약합니다.
            - 사용자가 공간을 예약할 때 사용합니다.
            
            ## 요청
            - `spaceId`: 공간 ID
            - 'spaceName' : 공간 이름
            - `startDate`: 예약 시작 날짜
            - `endDate`: 예약 종료 날짜
            - 'totalPrice' : 총 금
            """)
    @ApiResponse(
            responseCode = "200",
            description = "예약 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ReservationDtoV2 reserveV2(
            @CurrentUser User user,
            @RequestBody ReservationRequest request) {
        return reservationService.reserveV2(user, request);
    }

    @GetMapping("/v1/reservation/available-dates")
    @Operation(summary = "예약 가능 날짜 조회", description = """
            # 예약 가능 날짜 조회
            - 공간의 예약 가능 날짜를 조회합니다.
            - 사용자가 공간의 예약 가능 날짜를 조회할 때 사용합니다.
            
            ## 요청
            - `spaceId`: 공간 ID
            
            ## 응답
            - `reservedDates`: 예약된 날짜 목록
            """)
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ReservationAvailableDto getAvailableDates(
            @RequestParam Long spaceId) {
        return reservationService.getAvailableDates(spaceId);
    }

    // 게스트가 자신의 예약 취소
    @PostMapping("/v1/reservation/cancel")
    @Operation(summary = "예약 취소", description = """
            # 예약 취소
            - 게스트가 자신의 예약을 취소합니다.
            - 사용자가 자신의 예약을 취소할 때 사용합니다.
            
            ## 요청
            - `reservationId`: 예약 ID
            """)
    @ApiResponse(
            responseCode = "200",
            description = "취소 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void cancelReservation(
            @CurrentUser User user,
            @RequestParam Long reservationId) {
        reservationService.cancelReservation(user, reservationId);
    }

    // 호스트가 예약을 거절
    @PostMapping("/v1/reservation/decline")
    @Operation(summary = "예약 거절", description = """
            # 예약 거절
            - 호스트가 예약을 거절합니다.
            - 사용자가 예약을 거절할 때 사용합니다.
            
            ## 요청
            - `reservationId`: 예약 ID
            """)
    @ApiResponse(
            responseCode = "200",
            description = "거절 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void declineReservation(
            @CurrentUser User user,
            @RequestParam Long reservationId) {
        reservationService.declineReservation(user, reservationId);
    }

    // 호스트가 예약을 수락
    @PostMapping("/v1/reservation/accept")
    @Operation(summary = "예약 수락", description = """
            # 예약 수락
            - 호스트가 예약을 수락합니다.
            - 사용자가 예약을 수락할 때 사용합니다.
            
            ## 요청
            - `reservationId`: 예약 ID
            """)
    @ApiResponse(
            responseCode = "200",
            description = "수락 성공",
            useReturnTypeSchema = true)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void acceptReservation(
            @CurrentUser User user,
            @RequestParam Long reservationId) {
        reservationService.acceptReservation(user, reservationId);
    }

}
