package com.cmc.dice.domain.brand.application;

import com.cmc.dice.domain.brand.dao.BrandRepository;
import com.cmc.dice.domain.brand.domain.Brand;
import com.cmc.dice.domain.brand.dto.CreateBrandRequest;
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

	/**
	 * 브랜드 생성
	 */
	public SimpleBrandInfoDto createBrand(User user, CreateBrandRequest request) {
		return SimpleBrandInfoDto.of(brandRepository.save(CreateBrandRequest.toEntity(request)));
	}

	/**
	 * 브랜드 수정
	 */
	public SimpleBrandInfoDto updateBrand(User user, Long brandId, CreateBrandRequest request) {
		Brand brand = brandRepository.findById(brandId)
			.orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
		if (!brand.getAdmin().equals(user)) {
			throw new IllegalArgumentException("권한이 없습니다.");
		}
		brand.update(request);
		return SimpleBrandInfoDto.of(brandRepository.save(brand));
	}
}