package com.cmc.dice.domain.user.api;

import com.cmc.dice.domain.user.application.AuthService;
import com.cmc.dice.domain.user.application.GuestService;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.GuestInfoDto;
import com.cmc.dice.domain.user.dto.ReissueRequest;
import com.cmc.dice.domain.user.dto.UpdateGuestInfoRequest;
import com.cmc.dice.global.exception.ApiErrorResponse;
import com.cmc.dice.global.jwt.CurrentUser;
import com.cmc.dice.global.jwt.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
@Tag(name = "Guest")
public class GuestController {
	private final GuestService guestService;

	@GetMapping("/info")
	@Operation(summary = "게스트 정보 조회", description = """
			# 게스트 정보 조회
						
			- 게스트 정보를 조회합니다.
						
			## 응답
						
			- 조회 성공 시 200 코드와 게스트 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public GuestInfoDto getGuestInfo(@CurrentUser User user) {
		return guestService.getGuestInfo(user);
	}

	@PostMapping("/update")
	@Operation(summary = "게스트 정보 수정", description = """
			# 게스트 정보 수정
						
			- 게스트 정보를 수정합니다.
						
			## 응답
						
			- 수정 성공 시 200 코드와 수정된 게스트 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "수정 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public GuestInfoDto updateGuestInfo(
			@CurrentUser User user,
			@Valid @RequestBody UpdateGuestInfoRequest request) {
		return guestService.updateGuestInfo(user, request);
	}
}