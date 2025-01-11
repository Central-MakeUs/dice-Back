package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}
