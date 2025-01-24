package com.cmc.dice.domain.space.api;

import com.cmc.dice.domain.space.application.SpaceService;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
	@ResponseStatus(HttpStatus.CREATED)
	public void createSpace(
			@CurrentUser User user,
			@RequestBody CreateSpaceRequest request) {
		spaceService.createSpace(user, request);
	}


	@GetMapping("/latest")
	@Operation(summary = "최신 공간 조회", description = """
			# 최신 공간 조회
			최신 등록된 공간을 조회합니다.
			
			## 요청
			- `page`: 페이지 번호
			- `size`: 페이지 크기
			""")
	public Page<SpaceSimpleInfoDto> getSpacesByLatest(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return spaceService.getSpacesByLatest(PageRequest.of(page, size));
	}

	@PutMapping("/{id}")
	@Operation(summary = "공간 수정", description = """
            # 공간 수정
            공간 정보를 수정합니다.
            
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
	public Space updateSpace(
			@CurrentUser User user,
			@PathVariable Long id,
			@RequestBody CreateSpaceRequest request) {
		return spaceService.updateSpaceInfo(user, id, request);
	}
}