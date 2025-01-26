package com.cmc.dice.domain.announcement.dto;

import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

	private AnnouncementStatus status;
}