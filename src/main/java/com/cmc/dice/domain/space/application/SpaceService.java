package com.cmc.dice.domain.space.application;

import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.dao.SpaceTagRepository;
import com.cmc.dice.domain.space.dao.TagRepository;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.domain.SpaceTag;
import com.cmc.dice.domain.space.domain.Tag;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceInfoDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.space.exception.SpaceNotFoundException;
import com.cmc.dice.domain.space.exception.SpaceNotOwnerException;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {
	private final SpaceRepository spaceRepository;
	private final TagRepository tagRepository;
	private final SpaceTagRepository spaceTagRepository;
	private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

	@Transactional
	public void createSpace(User user, CreateSpaceRequest request) {
		Space space = new Space(user, request);
		space.updateLocation(geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude())));

		spaceRepository.save(space);

		request.getTags().forEach(tag -> {
			SpaceTag spaceTag = addTag(space, tag);
			spaceTagRepository.save(spaceTag);
			space.addSpaceTag(spaceTag);
		});

		spaceRepository.save(space);
	}

	public SpaceTag addTag(Space space, String tag) {
		Tag newTag = tagRepository.findByName(tag)
				.orElseGet(() -> tagRepository.save(Tag.builder().name(tag).build()));
        return SpaceTag.builder()
				.tag(newTag)
				.space(space)
				.build();
	}

	@Transactional(readOnly = true)
	public Page<SpaceSimpleInfoDto> getSpacesByLatest(Pageable pageable) {
		return spaceRepository.findAllByOrderByCreatedAtDesc(pageable)
				.map(SpaceSimpleInfoDto::of);
	}

	public SpaceInfoDto updateSpaceInfo(User user, Long id, CreateSpaceRequest request) {
		Space space = spaceRepository.findById(id)
				.orElseThrow(SpaceNotFoundException::new);

		if (!space.isOwner(user)) {
			throw new SpaceNotOwnerException();
		}

		space.update(request);
		return SpaceInfoDto.of(space);
	}

	/**
	 * 공간 필터링 조회
	 */
	public Page<SpaceSimpleInfoDto> getSpacesByFilter(SpaceFilterDto spaceFilterDto,User user, Pageable pageable) {
		return spaceRepository.findSpaces(spaceFilterDto, user, pageable);
	}

	/**
	 * 공간 상세 조회
	 */
	public SpaceInfoDto getSpaceInfo(User user, Long id) {
		return spaceRepository.findSpaceDetail(user, id)
				.orElseThrow(SpaceNotFoundException::new);
	}
}