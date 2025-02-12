package com.cmc.dice.global.infra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUrlDtos {
    @Schema(description = "이미지 URL 리스트", example = "[\"https://dice-image.s3.ap-northeast-2.amazonaws.com/1629780000000.jpg\"]")
    private List<String> imageUrls;
}
