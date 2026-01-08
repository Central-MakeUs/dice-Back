package com.cmc.dice.domain.space.dao;

import com.cmc.dice.domain.space.domain.SpaceAnalysisPeople;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceAnalysisRepository extends JpaRepository<SpaceAnalysisPeople, Long> {
    Optional<SpaceAnalysisPeople> findByLocation(String location);
}
