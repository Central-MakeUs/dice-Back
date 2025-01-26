package com.cmc.dice.domain.announcement.dto;

import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;

import java.time.LocalDateTime;
import java.util.List;

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
}