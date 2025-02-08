package com.cmc.dice.domain.announcement.application;

import com.cmc.dice.domain.announcement.dao.AnnouncementRepository;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.announcement.dto.AnnouncementInfoDto;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
	private final AnnouncementRepository announcementRepository;

	// 모집 공고 리스트 조회
	public Page<AnnouncementSimpleInfoDto> getAnnouncements(AnnouncementFilterRequest request, User user, Pageable pageable) {
		return announcementRepository.findAnnouncements(request, user, pageable);
	}


	// 모집 공고 상세 조회
	public AnnouncementInfoDto getAnnouncement(Long id) {
		return announcementRepository.findById(id)
			.map(AnnouncementInfoDto::fromEntity)
			.orElseThrow(() -> new IllegalArgumentException("해당 모집 공고가 존재하지 않습니다."));
	}
}