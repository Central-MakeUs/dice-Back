package com.cmc.dice.domain.brand.api;

import com.cmc.dice.domain.brand.application.BrandService;
import com.cmc.dice.domain.brand.dto.CreateBrandRequest;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
@Tag(name = "Brand")
public class BrandController {
	private final BrandService brandService;

	@GetMapping("/my")
	@Operation(summary = "자신의 브랜드 조회", description = """
            # 자신의 브랜드 조회
            
            - 로그인 된 사용자의 브랜드 정보를 조회합니다.
            
            ## 응답
            
            - 조회 성공 시 200 코드와 간단한 브랜드 정보 리스트로 응답합니다.
            """)
	@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public void logout(@CurrentUser User user) {
		brandService.getBrandInfo(user);
	}

	@PostMapping("/create")
	@Operation(summary = "브랜드 생성", description = """
			# 브랜드 생성
			
			- 브랜드를 생성합니다.
			
			## 응답
			
			- 생성 성공 시 201 코드와 생성된 브랜드 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "201",
			description = "생성 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	@ResponseStatus(HttpStatus.CREATED)
	public void createBrand(
			@CurrentUser User user,
			@RequestBody CreateBrandRequest request) {
		brandService.createBrand(user, request);
	}

	@PostMapping("/update/{brandId}")
	@Operation(summary = "브랜드 수정", description = """
			# 브랜드 수정
			
			- 브랜드를 수정합니다.
			
			## 응답
			
			- 수정 성공 시 200 코드와 수정된 브랜드 정보로 응답합니다.
			""")
	@ApiResponse(
			responseCode = "200",
			description = "수정 성공",
			useReturnTypeSchema = true
	)
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "access-token")
	public void updateBrand(
			@CurrentUser User user,
			@PathVariable Long brandId,
			@RequestBody CreateBrandRequest request) {
		brandService.updateBrand(user, brandId, request);
	}
}