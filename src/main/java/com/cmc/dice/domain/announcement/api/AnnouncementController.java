package com.cmc.dice.domain.announcement.api;

import com.cmc.dice.domain.announcement.application.AnnouncementService;
import com.cmc.dice.domain.announcement.dto.AnnouncementFilterRequest;
import com.cmc.dice.domain.announcement.dto.AnnouncementInfoDto;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.brand.application.BrandService;
import com.cmc.dice.domain.space.application.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/announcement")
@RequiredArgsConstructor
@Tag(name = "Announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping("/list")
    @Operation(summary = "모집 공고 리스트 조회", description = """
        # 모집 공고 리스트 조회
        
        - 모집 공고 리스트를 조회합니다.
        
        ## 요청
        
        - `page`: 페이지 번호
        - `size`: 페이지 크기
        """)
    public Page<AnnouncementSimpleInfoDto> getAnnouncements(
            @RequestBody(required = false) AnnouncementFilterRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return announcementService.getAnnouncements(request, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "모집 공고 상세 조회", description = """
        # 모집 공고 상세 조회
        
        - 모집 공고의 상세 정보를 조회합니다.
        
        ## 요청
        
        - `id`: 모집 공고 ID
        """)
    public AnnouncementInfoDto getAnnouncement(@PathVariable Long id) {
        return announcementService.getAnnouncement(id);
    }
}