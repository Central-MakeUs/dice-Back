package com.cmc.dice.domain.brand.application;

import com.cmc.dice.domain.brand.dao.BrandRepository;
import com.cmc.dice.domain.brand.domain.Brand;
import com.cmc.dice.domain.brand.dto.CreateBrandRequest;
import com.cmc.dice.domain.brand.dto.SimpleBrandInfoDto;
import com.cmc.dice.domain.brand.exception.InvalidBrandAdminException;
import com.cmc.dice.domain.brand.exception.NotFoundBrandException;
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
		List<Brand> brands = brandRepository.findByAdminId(user.getId());
		if (brands == null) {
			return null; // 혹은 Collections.emptyList() 반환 가능
		}
		return brands.stream()
				.map(SimpleBrandInfoDto::of)
				.toList();
	}

	/**
	 * 브랜드 생성
	 */
	public SimpleBrandInfoDto createBrand(User user, CreateBrandRequest request) {
		return SimpleBrandInfoDto.of(brandRepository.save(CreateBrandRequest.toEntity(user, request)));
	}

	/**
	 * 브랜드 수정
	 */
	public SimpleBrandInfoDto updateBrand(User user, Long brandId, CreateBrandRequest request) {
		Brand brand = brandRepository.findById(brandId)
			.orElseThrow(() -> new NotFoundBrandException());
		if (!brand.getAdmin().getId().equals(user.getId())) {
			throw new InvalidBrandAdminException();
		}
		brand.update(request);
		return SimpleBrandInfoDto.of(brandRepository.save(brand));
	}
}