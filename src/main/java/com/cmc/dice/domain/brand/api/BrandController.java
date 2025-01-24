package com.cmc.dice.domain.brand.api;

import com.cmc.dice.domain.brand.application.BrandService;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}