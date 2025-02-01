package com.cmc.dice.domain.announcement.dao;


import com.cmc.dice.domain.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {
    Collection<Announcement> findByIdIn(List<Long> announcementIdList);
}