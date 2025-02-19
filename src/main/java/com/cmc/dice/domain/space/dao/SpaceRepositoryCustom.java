package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceInfoDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface SpaceRepositoryCustom {
	Page<SpaceSimpleInfoDto> findSpaces(SpaceFilterDto spaceFilterDto, String keyword, User user, Pageable pageable);

	Optional<SpaceInfoDto> findSpaceDetail(User user, Long id);

}