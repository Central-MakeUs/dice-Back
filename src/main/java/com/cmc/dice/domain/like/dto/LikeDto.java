package com.cmc.dice.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private Boolean isLike;
    private Long id;
}
