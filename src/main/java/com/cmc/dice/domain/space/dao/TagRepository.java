package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String tag);
}
