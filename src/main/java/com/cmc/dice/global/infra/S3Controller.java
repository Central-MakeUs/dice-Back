package com.cmc.dice.global.infra;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
@Tag(name = "S3")
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ImageUrlDto uploadImage(
            @CurrentUser User user,
            @RequestBody MultipartFile images) {

        if (images == null) {
            throw new NullFileException();
        }

        String imageUrl = s3Uploader.saveFile(user, images);
        return new ImageUrlDto(imageUrl);
    }

    @PostMapping(value = "/uploads", consumes = {"multipart/form-data"})
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public ImageUrlDtos uploadImages(
            @CurrentUser User user,
            @RequestBody MultipartFile[] images) {

        if (images == null || images.length == 0) {
            throw new NullFileException();
        }

        List<String> urls = s3Uploader.saveFiles(user, Arrays.stream(images).toList());
        return new ImageUrlDtos(urls);
    }
}
