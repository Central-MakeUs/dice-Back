package com.cmc.dice.domain.announcement.dto;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementSimpleInfoDto {
	private Long id;

	private String title;

	private String city;
	private String district;

	private String hostName;

	private String target;

	private LocalDateTime recruitmentStartAt;
	private LocalDateTime recruitmentEndAt;

	private Integer likeCount;

	private Boolean isLiked;

	private AnnouncementStatus status;

	public static AnnouncementSimpleInfoDto fromEntity(Announcement announcement) {
		return AnnouncementSimpleInfoDto.builder()
			.id(announcement.getId())
			.title(announcement.getName())
			.city(announcement.getCity())
			.district(announcement.getDistrict())
			.hostName(announcement.getHostName())
			.target(announcement.getTarget())
			.recruitmentStartAt(announcement.getRecruitmentStartAt())
			.recruitmentEndAt(announcement.getRecruitmentEndAt())
			.likeCount(announcement.getLikeCount())
			.status(announcement.getStatus())
			.build();
	}

	public AnnouncementSimpleInfoDto(Announcement announcement, Boolean isLiked) {
		this.id = announcement.getId();
		this.title = announcement.getName();
		this.city = announcement.getCity();
		this.district = announcement.getDistrict();
		this.hostName = announcement.getHostName();
		this.target = announcement.getTarget();
		this.recruitmentStartAt = announcement.getRecruitmentStartAt();
		this.recruitmentEndAt = announcement.getRecruitmentEndAt();
		this.likeCount = announcement.getLikeCount();
		this.status = announcement.getStatus();
	}
}