package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.SpaceTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceTagRepository extends JpaRepository<SpaceTag, Long> {
    Optional<SpaceTag> findBySpaceIdAndTagName(Long id, String tag);
}
