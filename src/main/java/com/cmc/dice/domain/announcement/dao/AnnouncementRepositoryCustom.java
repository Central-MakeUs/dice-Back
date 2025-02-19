package com.cmc.dice.domain.announcement.dao;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.announcement.dto.AnnouncementInfoDto;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface AnnouncementRepositoryCustom {
	Page<AnnouncementSimpleInfoDto> findAnnouncements(AnnouncementFilterRequest request, String keyword, User user, Pageable pageable);

	Optional<AnnouncementInfoDto> findAnnouncementDetail(User user, Long id);
}