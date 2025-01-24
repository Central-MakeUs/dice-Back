package com.cmc.dice.domain.user.application;

import com.cmc.dice.domain.brand.application.BrandService;
import com.cmc.dice.domain.brand.dto.SimpleBrandInfoDto;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.GuestInfoDto;
import com.cmc.dice.domain.user.dto.UpdateGuestInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
	private final UserRepository userRepository;

	private final BrandService brandService;

	/**
	 * 게스트 마이페이지 조회
	 */
	public GuestInfoDto getGuestInfo(User user) {
		List<SimpleBrandInfoDto> brandInfo = brandService.getBrandInfo(user);
		return GuestInfoDto.of(user, brandInfo);
	}

	/**
	 * 게스트 마이페이지 수정
	 */
	public GuestInfoDto updateGuestInfo(User user, UpdateGuestInfoRequest request) {
		user.update(request);
		userRepository.save(user);

		return GuestInfoDto.of(user, brandService.getBrandInfo(user));
	}
}