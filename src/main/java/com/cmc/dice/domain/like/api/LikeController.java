package com.cmc.dice.domain.like.api;

import com.cmc.dice.domain.like.application.LikeService;
import com.cmc.dice.domain.like.dto.LikeDto;
import com.cmc.dice.domain.space.application.SpaceService;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.space.dto.SpaceFilterDto;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.jwt.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
@Tag(name = "Like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/space/{id}")
    @Operation(summary = "공간 좋아요", description = """
            # 공간 좋아요
            사용자의 공간 좋아요를 토글합니다.
            
            ## 요청
            - `id`: 공간 ID
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public LikeDto likeSpace(
            @CurrentUser User user,
            @PathVariable Long id) {
        return likeService.likeSpace(user, id);
    }

    @PostMapping("/like/announcement/{id}")
    @Operation(summary = "공고 좋아요", description = """
            # 공고 좋아요
            사용자의 공고 좋아요를 토글합니다.
            
            ## 요청
            - `id`: 공고 ID
            """)
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public LikeDto likeAnnouncement(
            @CurrentUser User user,
            @PathVariable Long id) {
        return likeService.likeAnnouncement(user, id);
    }
}
