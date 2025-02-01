package com.cmc.dice.domain.announcement.dto;

import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementInfoDto {
	private Long id;

	private String title;

	private String city;
	private String district;
	private String address;

	private String hostName;

	private String target;

	private List<String> imageUrls;

	private LocalDateTime recruitmentStartAt;
	private LocalDateTime recruitmentEndAt;

	private String details;
	private String contactNumber;
	private String websiteUrl;

	private Integer likeCount;

	private AnnouncementStatus status;

	public static AnnouncementInfoDto fromEntity(Announcement announcement) {
		return AnnouncementInfoDto.builder()
				.id(announcement.getId())
				.title(announcement.getName())
				.city(announcement.getCity())
				.district(announcement.getDistrict())
				.address(announcement.getAddress())
				.hostName(announcement.getHostName())
				.target(announcement.getTarget())
				.imageUrls(announcement.getImageUrls())
				.recruitmentStartAt(announcement.getRecruitmentStartAt())
				.recruitmentEndAt(announcement.getRecruitmentEndAt())
				.details(announcement.getDetails())
				.contactNumber(announcement.getContactNumber())
				.websiteUrl(announcement.getWebsiteUrl())
				.likeCount(announcement.getLikeCount())
				.status(announcement.getStatus())
				.build();
	}
}