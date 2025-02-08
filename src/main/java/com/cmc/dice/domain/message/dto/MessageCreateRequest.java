package com.cmc.dice.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequest {
	private Long hostId;
	private Long spaceId;
}