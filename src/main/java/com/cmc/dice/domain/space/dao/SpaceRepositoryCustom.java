package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import java.util.List;

public interface SpaceRepositoryCustom {
	Page<SpaceSimpleInfoDto> findSpaces(SpaceFilterDto spaceFilterDto, User user, Pageable pageable);
}