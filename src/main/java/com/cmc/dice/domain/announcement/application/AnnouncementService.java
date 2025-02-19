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
	public Page<AnnouncementSimpleInfoDto> getAnnouncements(AnnouncementFilterRequest request, String keyword, User user, Pageable pageable) {
		return announcementRepository.findAnnouncements(request, keyword, user, pageable);
	}


	// 모집 공고 상세 조회
	public AnnouncementInfoDto getAnnouncement(User user, Long id) {
		return announcementRepository.findAnnouncementDetail(user, id)
				.orElseThrow(() -> new IllegalArgumentException("모집 공고를 찾을 수 없습니다."));
	}
}