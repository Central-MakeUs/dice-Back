package com.cmc.dice.domain.user.api;

import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.application.GuestService;
import com.cmc.dice.domain.user.application.HostService;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.GuestInfoDto;
import com.cmc.dice.domain.user.dto.HostInfoDto;
import com.cmc.dice.domain.user.dto.UpdateGuestInfoRequest;
import com.cmc.dice.domain.user.dto.UpdateHostInfoRequest;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/host")
@RequiredArgsConstructor
@Tag(name = "Host")
public class HostController {
	private final GuestService guestService;
	private final HostService hostService;

	@GetMapping("/info")
	@Operation(summary = "호스트 정보 조회", description = """
			# 호스트 정보 조회
						
			- 호스트 정보를 조회합니다.
						
			## 응답
						
			- 조회 성공 시 200 코드와 호스트 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public HostInfoDto getHostInfo(@CurrentUser User user) {
		return hostService.getHostInfo(user);
	}

	@PostMapping("/update")
	@Operation(summary = "호스트 정보 수정", description = """
			# 호스트 정보 수정
						
			- 호스트 정보를 수정합니다.
						
			## 응답
						
			- 수정 성공 시 200 코드와 호스트 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "수정 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public HostInfoDto updateHostInfo(
			@CurrentUser User user,
			@Valid @RequestBody UpdateHostInfoRequest request) {
		return hostService.updateHostInfo(user, request);
	}

	@GetMapping("/space")
	@Operation(summary = "호스트 공간 조회", description = """
			# 호스트 공간 조회
						
			- 호스트의 공간 정보를 조회합니다.
						
			## 응답
						
			- 조회 성공 시 200 코드와 호스트의 공간 정보 리스트로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public List<SpaceSimpleInfoDto> getHostSpace(
			@CurrentUser User user,
			@RequestParam(required = false) String keyword) {
		return hostService.getHostSpace(user, keyword);
	}
}