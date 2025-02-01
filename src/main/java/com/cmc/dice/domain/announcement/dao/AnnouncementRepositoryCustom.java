package com.cmc.dice.domain.announcement.dao;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

public interface AnnouncementRepositoryCustom {
	Page<Announcement> findAnnouncements(AnnouncementFilterRequest request, Pageable pageable);
}