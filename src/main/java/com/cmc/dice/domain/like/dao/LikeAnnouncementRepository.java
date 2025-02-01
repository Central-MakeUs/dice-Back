package com.cmc.dice.domain.like.dao;

import com.cmc.dice.domain.like.domain.LikeAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeAnnouncementRepository extends JpaRepository<LikeAnnouncement, Long> {
    Optional<LikeAnnouncement> findByUserIdAndAnnouncementId(Long id, Long announcementId);

    Page<LikeAnnouncement> findByUserId(Long id, Pageable pageable);
}
