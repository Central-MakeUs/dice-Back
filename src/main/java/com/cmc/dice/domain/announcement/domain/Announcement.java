package com.cmc.dice.domain.announcement.domain;

import com.cmc.dice.domain.space.domain.ImageUrlListConverter;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcements")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User host;

    @Column(nullable = false)
    private String hostName;

    @Column(nullable = false)
    private String name;

    @Convert(converter = ImageUrlListConverter.class)
    @Column(columnDefinition = "TEXT")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String websiteUrl;

    // 지원 공고 소개
    @Lob
    private String details;

    // 모집 기간
    private LocalDateTime recruitmentStartAt;
    private LocalDateTime recruitmentEndAt;

    // 지원 대상 (소상공인, 중소기업 등)
    private String target;

    // 모집 상태
    @Enumerated(EnumType.STRING)
    private AnnouncementStatus status;

    // 좋아요 수
    @Builder.Default
    private int likeCount = 0;
}