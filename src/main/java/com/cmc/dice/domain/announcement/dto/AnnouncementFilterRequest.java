package com.cmc.dice.domain.announcement.dto;

import com.cmc.dice.domain.announcement.domain.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementFilterRequest {
	private String city;
	private String district;

	private List<String> targets;

	private AnnouncementStatus status;
}