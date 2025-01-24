package com.cmc.dice.domain.brand.application;

import com.cmc.dice.domain.brand.dao.BrandRepository;
import com.cmc.dice.domain.brand.dto.SimpleBrandInfoDto;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
	private final BrandRepository brandRepository;

	/**
	 * 로그인 된 사용자의 브랜드 정보를 조회한다.
	 */
	public List<SimpleBrandInfoDto> getBrandInfo(User user) {
		return brandRepository.findByAdmin(user).stream()
			.map(SimpleBrandInfoDto::of)
			.toList();
	}
}