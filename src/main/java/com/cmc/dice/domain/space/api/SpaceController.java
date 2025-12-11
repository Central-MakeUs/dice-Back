package com.cmc.dice.domain.space.api;

import com.cmc.dice.domain.space.application.SpaceService;
import com.cmc.dice.domain.space.dto.*;
import com.cmc.dice.domain.space.dto.SpaceInfoDtoV2;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Space")
public class SpaceController {
	private final SpaceService spaceService;

	@Deprecated
	@PostMapping("/v1/space/register")
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
			- `size`: 공간 크기
			- `tags`: 태그 리스트
			- `pricePerDay`: 일일 가격
			- `discountRate`: 할인율
			- `details`: 상세 정보
			- `longitude`: 경도
			- `latitude`: 위도
			- `city`: 도시
			- `district`: 구
			- `address`: 주소
			- `detailAddress`: 상세 주소
			- `websiteUrl`: 웹사이트 URL
			- `contactNumber`: 연락처
			- `facilityInfo`: 시설 정보
			- `notice`: 공지사항
			- 'isActivated': 활성화 여부
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	@ResponseStatus(HttpStatus.CREATED)
	public void createSpace(
			@CurrentUser User user,
			@RequestBody CreateSpaceRequest request) {
		spaceService.createSpace(user, request);
	}

	@PostMapping("/v2/space/register")
	@Operation(summary = "공간 등록 ver2", description = """
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
			- `size`: 공간 크기
			- `tags`: 태그 리스트
			- `pricePerDay`: 일일 가격
			- `discountRate`: 할인율
			- `details`: 상세 정보
			- `longitude`: 경도
			- `latitude`: 위도
			- `city`: 도시
			- `district`: 구
			- `address`: 주소
			- `detailAddress`: 상세 주소
			- `websiteUrl`: 웹사이트 URL
			- `contactNumber`: 연락처
			- `facilityInfo`: 시설 정보
			- `notice`: 공지사항
			- 'isActivated': 활성화 여부
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	@ResponseStatus(HttpStatus.CREATED)
	public void createSpaceV2(
			@CurrentUser User user,
			@RequestBody CreateSpaceRequestV2 request) {
		spaceService.createSpaceV2(user, request);
	}

	@PostMapping("/v1/space/update/{id}")
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
			- `size`: 공간 크기
			- `tags`: 태그 리스트
			- `pricePerDay`: 일일 가격
			- `discountRate`: 할인율
			- `details`: 상세 정보
			- `longitude`: 경도
			- `latitude`: 위도
			- `city`: 도시
			- `district`: 구
			- `address`: 주소
			- `detailAddress`: 상세 주소
			- `websiteUrl`: 웹사이트 URL
			- `contactNumber`: 연락처
			- `facilityInfo`: 시설 정보
			- `notice`: 공지사항
			- 'isActivated': 활성화 여부
            """)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public SpaceInfoDtoV2 updateSpace(
			@CurrentUser User user,
			@PathVariable Long id,
			@RequestBody @Valid CreateSpaceRequest request) {
		return spaceService.updateSpaceInfo(user, id, request);
	}

	@PostMapping("/v1/space/list")
	@Operation(summary = "공간 필터링 조회", description = """
			# 공간 필터링 조회
			공간을 필터링하여 조회합니다.
			활성화된 공간 중 조회합니다.
			
			## 요청
			- `spaceFilterDto`: 공간 필터 DTO
			- `page`: 페이지 번호
			- `size`: 페이지 크기
			
		    ### 필터링 정보 상세
		    - `keyword`: 검색어 (공간 이름 기반 검색)
		    - `city`: 도시 (서울, 부산 ...)
		    - `district`: 구 (강남구, 강동구 ...)
		    - `minPrice`: 최소 가격
		    - `maxPrice`: 최대 가격
		    - `minCapacity`: 최소 수용 인원
		    - `maxCapacity`: 최대 수용 인원
		    - `sort`: 정렬 방식 (latest, likeCount, priceAsc, priceDesc)
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public Page<SpaceSimpleInfoDto> getSpacesByFilter(
			@CurrentUser User user,
			@RequestBody(required = false) SpaceFilterDto spaceFilterDto,
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return spaceService.getSpacesByFilter(spaceFilterDto, keyword, user, PageRequest.of(page, size));
	}

	@PostMapping("/v2/space/list")
	@Operation(summary = "공간 필터링 조회 ver2", description = """
   			**현재 내부 로직에서는 v1과 동일하게 동작합니다. api 전달이 잘되는지만 확인해주시길 바랍니다.**
   			
			# 공간 필터링 조회
			공간을 필터링하여 조회합니다.
			활성화된 공간 중 조회합니다.
			
			## 요청
			- `spaceFilterDto`: 공간 필터 DTO
			- `page`: 페이지 번호
			- `size`: 페이지 크기
			
		    ### 필터링 정보 상세
		    - `keyword`: 검색어 (공간 이름 기반 검색)
		    - `city`: 도시 (서울, 부산 ...)
		    - `district`: 구 (강남구, 강동구 ...)
		    - `gender`: 성별
		    - `ageGroup`: 연령대
		    - `dayOfWeek`: 요일 
		    - `minPrice`: 최소 가격 (0~100만)
		    - `maxPrice`: 최대 가격 (0~100만)
		    - `minSize`: 최소 평수 (0~150)
		    - `maxSize`: 최대 평수 (0~150)
		    - `sort`: 정렬 방식 (latest, likeCount, priceAsc, priceDesc)
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public Page<SpaceSimpleInfoDto> getSpacesByFilterV2(
			@CurrentUser User user,
			@RequestBody(required = false) SpaceFilterDtoV2 spaceFilterDtoV2,
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return spaceService.getSpacesByFilterV2(spaceFilterDtoV2, keyword, user, PageRequest.of(page, size));
	}

	@GetMapping("/v2/space/{id}")
	@Operation(summary = "공간 상세 조회 ver2", description = """
			# 공간 상세 조회
			공간의 상세 정보를 조회합니다.
			
			## 요청
			- `id`: 공간 ID
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public SpaceInfoDtoV2 getSpaceInfoV2(
			@CurrentUser User user,
			@PathVariable Long id) {
		return spaceService.getSpaceInfoV2(user, id);
	}

	@Deprecated
	@GetMapping("/v1/space/{id}")
	@Operation(summary = "공간 상세 조회", description = """
			# 공간 상세 조회
			공간의 상세 정보를 조회합니다.
			
			## 요청
			- `id`: 공간 ID
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public SpaceInfoDto getSpaceInfo(
			@CurrentUser User user,
			@PathVariable Long id) {
		return spaceService.getSpaceInfo(user, id);
	}

	@Deprecated
	@GetMapping("/v1/space/{id}/analysis")
	@Operation(summary = "공간 인구 분석", description = """
			# 공간 상세 조회
			공간의 상세 정보를 조회합니다.
			
			## 요청
			- `id`: 공간 ID
			""")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public SpaceInfoDto getSpaceInfoAnalysis(
			@CurrentUser User user,
			@PathVariable Long id) {
		return spaceService.getSpaceInfo(user, id);
	}
}
