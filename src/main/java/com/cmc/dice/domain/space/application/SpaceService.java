package com.cmc.dice.domain.space.application;

import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {
	private final SpaceRepository spaceRepository;

	@Transactional
	public Space createSpace(User user, CreateSpaceRequest request) {

		Space space = new Space(user, request);

		return spaceRepository.save(space);
	}
}