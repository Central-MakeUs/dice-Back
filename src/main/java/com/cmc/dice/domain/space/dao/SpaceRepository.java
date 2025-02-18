package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long>, SpaceRepositoryCustom {
	Page<Space> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Collection<Space> findByIdIn(List<Long> spaceIdList);

    Collection<Space> findByAdmin(User user);
}