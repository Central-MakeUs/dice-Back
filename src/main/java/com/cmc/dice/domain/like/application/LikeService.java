package com.cmc.dice.domain.like.application;

import com.cmc.dice.domain.announcement.dao.AnnouncementRepository;
import com.cmc.dice.domain.announcement.domain.Announcement;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.like.dao.LikeAnnouncementRepository;
import com.cmc.dice.domain.like.dao.LikeSpaceRepository;
import com.cmc.dice.domain.like.domain.LikeAnnouncement;
import com.cmc.dice.domain.like.domain.LikeSpace;
import com.cmc.dice.domain.like.dto.LikeDto;
import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.domain.Space;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeSpaceRepository likeSpaceRepository;
    private final LikeAnnouncementRepository likeAnnouncementRepository;

    private final SpaceRepository spaceRepository;
    private final AnnouncementRepository announcementRepository;

    // 공간 좋아요
    public LikeDto likeSpace(User user, Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공간입니다."));

        boolean isLiked = likeSpaceRepository.findByUserIdAndSpaceId(user.getId(), spaceId)
                .map(existingLike -> {
                    likeSpaceRepository.delete(existingLike);
                    space.decreaseLikeCount();
                    return false;
                }).orElseGet(() -> {
                    likeSpaceRepository.save(LikeSpace.builder()
                            .user(user)
                            .space(space)
                            .build());
                    space.increaseLikeCount();
                    return true;
                });

        spaceRepository.save(space);

        return new LikeDto(
                isLiked,
                spaceId
        );
    }

    // 공고 좋아요
    public LikeDto likeAnnouncement(User user, Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공고입니다."));

        boolean isLiked = likeAnnouncementRepository.findByUserIdAndAnnouncementId(user.getId(), announcementId)
                .map(existingLike -> {
                    likeAnnouncementRepository.delete(existingLike);
                    announcement.decreaseLikeCount();
                    return false;
                }).orElseGet(() -> {
                    likeAnnouncementRepository.save(LikeAnnouncement.builder()
                            .user(user)
                            .announcement(announcement)
                            .build());
                    announcement.increaseLikeCount();
                    return true;
                });

        announcementRepository.save(announcement);

        return new LikeDto(
                isLiked,
                announcementId
        );
    }

    // 공간 좋아요 리스트 조회
    public Page<SpaceSimpleInfoDto> getLikeSpaceList(User user, Pageable pageable) {
        Page<LikeSpace> likeSpacePage = likeSpaceRepository.findByUserId(user.getId(), pageable);
        List<Long> spaceIdList = likeSpacePage.stream()
                .map(likeSpace -> likeSpace.getSpace().getId())
                .collect(Collectors.toList());

        List<SpaceSimpleInfoDto> spaceDtoList = spaceRepository.findByIdIn(spaceIdList).stream()
                .map(spaceSimpleInfoDto -> new SpaceSimpleInfoDto(spaceSimpleInfoDto, true))
                .collect(Collectors.toList());

        return new PageImpl<>(spaceDtoList, pageable, likeSpacePage.getTotalElements());
    }

    // 공고 좋아요 리스트 조회
    public Page<AnnouncementSimpleInfoDto> getLikeAnnouncementList(User user, Pageable pageable) {
        Page<LikeAnnouncement> likeAnnouncementPage = likeAnnouncementRepository.findByUserId(user.getId(), pageable);
        List<Long> announcementIdList = likeAnnouncementPage.stream()
                .map(likeAnnouncement -> likeAnnouncement.getAnnouncement().getId())
                .collect(Collectors.toList());

        List<AnnouncementSimpleInfoDto> announcementDtoList = announcementRepository.findByIdIn(announcementIdList).stream()
                .map(announcementSimpleInfoDto -> new AnnouncementSimpleInfoDto(announcementSimpleInfoDto, true))
                .collect(Collectors.toList());

        return new PageImpl<>(announcementDtoList, pageable, likeAnnouncementPage.getTotalElements());
    }
}