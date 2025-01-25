package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import java.util.List;

public interface SpaceRepositoryCustom {
	Page<Space> findSpaces(Point location, Integer radius, Integer minCapacity, String sortBy, Pageable pageable);

	Page<Space> findSpaces(String city, String district, Integer minCapacity, String sortBy, Pageable pageable);
}