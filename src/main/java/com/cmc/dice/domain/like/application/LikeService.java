package com.cmc.dice.domain.like.application;

import com.cmc.dice.domain.announcement.dao.AnnouncementRepository;
import com.cmc.dice.domain.like.dao.LikeAnnouncementRepository;
import com.cmc.dice.domain.like.dao.LikeSpaceRepository;
import com.cmc.dice.domain.like.domain.LikeAnnouncement;
import com.cmc.dice.domain.like.domain.LikeSpace;
import com.cmc.dice.domain.like.dto.LikeDto;
import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
