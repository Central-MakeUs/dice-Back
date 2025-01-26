package com.cmc.dice.domain.announcement.dao;


import com.cmc.dice.domain.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom {
}