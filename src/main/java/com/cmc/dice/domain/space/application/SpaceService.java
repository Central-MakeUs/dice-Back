package com.cmc.dice.domain.space.application;

import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceInfoDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.space.exception.SpaceNotFoundException;
import com.cmc.dice.domain.space.exception.SpaceNotOwnerException;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {
	private final SpaceRepository spaceRepository;

	@Transactional
	public void createSpace(User user, CreateSpaceRequest request) {
		Space space = new Space(user, request);
		spaceRepository.save(space);
	}

	@Transactional(readOnly = true)
	public Page<SpaceSimpleInfoDto> getSpacesByLatest(Pageable pageable) {
		return spaceRepository.findAllByOrderByCreatedAtDesc(pageable)
				.map(SpaceSimpleInfoDto::of);
	}

	public Space updateSpaceInfo(User user, Long id, CreateSpaceRequest request) {
		Space space = spaceRepository.findById(id)
				.orElseThrow(SpaceNotFoundException::new);

		if (!space.isOwner(user)) {
			throw new SpaceNotOwnerException();
		}

		space.update(request);
		return space;
	}

	/**
	 * 공간 필터링 조회
	 */
	public Page<SpaceSimpleInfoDto> getSpacesByFilter(SpaceFilterDto spaceFilterDto, Pageable pageable) {
		Page<Space> spaces = spaceRepository.findSpaces(spaceFilterDto, pageable);
		return spaces.map(SpaceSimpleInfoDto::of);
	}

	/**
	 * 공간 상세 조회
	 */
	public SpaceInfoDto getSpaceInfo(Long id) {
		Space space = spaceRepository.findById(id)
				.orElseThrow(SpaceNotFoundException::new);
		return SpaceInfoDto.of(space);
	}
}