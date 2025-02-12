package com.cmc.dice.global.infra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUrlDto {
    @Schema(description = "이미지 URL", example = "https://dice-image.s3.ap-northeast-2.amazonaws.com/1629780000000.jpg")
    private String imageUrl;
}
