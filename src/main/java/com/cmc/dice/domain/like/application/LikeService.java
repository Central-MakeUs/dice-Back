package com.cmc.dice.domain.like.application;

import com.cmc.dice.domain.announcement.dao.AnnouncementRepository;
import com.cmc.dice.domain.announcement.dto.AnnouncementSimpleInfoDto;
import com.cmc.dice.domain.like.dao.LikeAnnouncementRepository;
import com.cmc.dice.domain.like.dao.LikeSpaceRepository;
import com.cmc.dice.domain.like.domain.LikeAnnouncement;
import com.cmc.dice.domain.like.domain.LikeSpace;
import com.cmc.dice.domain.like.dto.LikeDto;
import com.cmc.dice.domain.space.dao.SpaceRepository;
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
        likeSpaceRepository.findByUserIdAndSpaceId(user.getId(), spaceId)
                .ifPresentOrElse(
                        likeSpaceRepository::delete,
                        () -> likeSpaceRepository.save(LikeSpace.builder()
                                .user(user)
                                .space(spaceRepository.getReferenceById(spaceId))
                                .build())
                );

        return new LikeDto(
                likeSpaceRepository.findByUserIdAndSpaceId(user.getId(), spaceId).isPresent(),
                spaceId
        );
    }

    // 공고 좋아요
    public LikeDto likeAnnouncement(User user, Long announcementId) {
        likeAnnouncementRepository.findByUserIdAndAnnouncementId(user.getId(), announcementId)
                .ifPresentOrElse(
                        likeAnnouncementRepository::delete,
                        () -> likeAnnouncementRepository.save(LikeAnnouncement.builder()
                                .user(user)
                                .announcement(announcementRepository.getReferenceById(announcementId))
                                .build())
                );

        return new LikeDto(
                likeAnnouncementRepository.findByUserIdAndAnnouncementId(user.getId(), announcementId).isPresent(),
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