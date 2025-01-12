package com.cmc.dice.domain.space.api;

import com.cmc.dice.domain.space.application.SpaceService;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/space")
@RequiredArgsConstructor
@Tag(name = "Space")
public class SpaceController {
	private final SpaceService spaceService;

	@PostMapping("/register")
	@Operation(summary = "공간 등록", description = """
			# 공간 등록
			사용자의 공간을 등록합니다.
			
			## 요청
			- `name`: 공간 이름
			- `description`: 공간 설명
			- `imageUrls`: 이미지 URL 리스트
			- `category`: 공간 카테고리
			- `openingTime`: 오픈 시간
			- `closingTime`: 마감 시간
			- `capacity`: 수용 인원
			- `tags`: 태그 리스트
			- `pricePerDay`: 일일 가격
			- `discountRate`: 할인율
			- `details`: 상세 정보
			- `location`: 위치 정보
			- `websiteUrl`: 웹사이트 URL
			- `contactNumber`: 연락처
			- `facilityInfo`: 시설 정보
			- `notice`: 공지사항
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public void createSpace(
			@CurrentUser User user,
			@RequestBody CreateSpaceRequest request) {
		spaceService.createSpace(user, request);
	}
}