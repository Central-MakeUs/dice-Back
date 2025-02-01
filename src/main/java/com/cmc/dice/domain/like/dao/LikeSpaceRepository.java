package com.cmc.dice.domain.like.dao;

import com.cmc.dice.domain.like.domain.LikeSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeSpaceRepository extends JpaRepository<LikeSpace, Long> {
    Optional<LikeSpace> findByUserIdAndSpaceId(Long id, Long spaceId);

    Page<LikeSpace> findByUserId(Long id, Pageable pageable);
}
