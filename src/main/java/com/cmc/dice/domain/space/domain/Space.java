package com.cmc.dice.domain.space.domain;


import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.space.dto.CreateSpaceRequestV2;
import com.cmc.dice.domain.space.dto.FacilityInfoDto;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "spaces")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Space extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "space_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User admin; // 공간 소유자

    @Column(nullable = false)
    private String name; // 공간 이름

    @Convert(converter = ImageUrlListConverter.class)
    @Column(columnDefinition = "TEXT") // MySQL에서 JSON 타입 또는 TEXT 타입으로 저장
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceCategory category; // 공간 카테고리 (예: 갤러리, 카페 등)

    private LocalTime openingTime; // 공간 운영 시작 시간
    private LocalTime closingTime; // 공간 운영 마감 시간

    // TODO: 수용인원 관리 x
    // 면적 계산해서 저장.
    @Column(nullable = false)
    private int capacity; // 수용 인원

    @Column(nullable = false)
    private int size; //공간 크기

    @OneToMany(mappedBy = "space")
    private List<SpaceTag> tags;

    // 공간 대여 가격 작성
    @Column(nullable = false)
    private int pricePerDay; // 1일 대여 비용

    private int discountRate; // 할인율 (%)

    private int discountPrice; // 할인된 가격

    // 공간 상세 소개 작성
    @Lob
    private String details; // 공간 상세 소개

    // 공간 위치 정보
    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Point location;

    // 주소
    private String city;
    private String district;
    private String address;
    private String detailAddress;

    private String websiteUrl; // 웹사이트 URL
    private String contactNumber; // 연락처
    private String badge; // 뱃지 내용 (20대 여성 방문 상위 10%)

    // 시설 이용 및 공지사항 안내 작성
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpaceFacility> facilityInfos; // 시설 이용 안내

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "nearest_subway_id")
    private SpaceNearestSubway nearestSubway;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_people_id")
    private SpaceAnalysisPeople analysisPeople;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "notices", joinColumns = @JoinColumn(name = "space_id"))
    private List<String> notices; // 공지사항

    @Builder.Default
    private int likeCount = 0; // 좋아요 수

    @Builder.Default
    private boolean isActivated = true; // 활성화 여부

    public Space(User user, CreateSpaceRequest request) {
        this.admin = user;
        this.name = request.getName();
        this.imageUrls = request.getImageUrls();
        this.category = request.getCategory();
        this.openingTime = LocalTime.parse(request.getOpeningTime(), DateTimeFormatter.ofPattern("HH:mm"));
        this.closingTime = LocalTime.parse(request.getClosingTime(), DateTimeFormatter.ofPattern("HH:mm"));

        this.capacity = request.getCapacity();
        this.size = request.getSize();

        this.pricePerDay = request.getPricePerDay();
        this.discountRate = request.getDiscountRate();
        this.discountPrice = this.pricePerDay * (100 - this.discountRate) / 100;
        this.details = request.getDetails();

        this.city = request.getCity();
        this.district = request.getDistrict();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();

        this.websiteUrl = request.getWebsiteUrl();
        this.contactNumber = request.getContactNumber();
        this.facilityInfos = request.getFacilityInfos()
                .stream()
                .map(FacilityInfoDto::toEntity)
                .toList();
        this.notices = request.getNotices().stream().toList();
        this.isActivated = true;
    }

    public Space(User user, CreateSpaceRequestV2 request) {
        this.admin = user;
        this.name = request.getName();
        this.imageUrls = request.getImageUrls();
        this.category = request.getCategory();
        this.openingTime = LocalTime.parse(request.getOpeningTime(), DateTimeFormatter.ofPattern("HH:mm"));
        this.closingTime = LocalTime.parse(request.getClosingTime(), DateTimeFormatter.ofPattern("HH:mm"));

        this.capacity = request.getCapacity();
        this.size = request.getSize();

        this.pricePerDay = request.getPricePerDay();
        this.discountRate = request.getDiscountRate();
        this.discountPrice = this.pricePerDay * (100 - this.discountRate) / 100;
        this.details = request.getDetails();

        this.city = request.getCity();
        this.district = request.getDistrict();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();

        this.contactNumber = request.getContactNumber();
        this.facilityInfos = request.getFacilityInfos()
                .stream()
                .map(FacilityInfoDto::toEntity)
                .collect(Collectors.toCollection(ArrayList::new));
        this.notices = new ArrayList<>(request.getNotices());
        this.isActivated = true;
    }

    public boolean isOwner(User user) {
        return this.admin.equals(user.getId());
    }

    public void update(CreateSpaceRequest request) {
        this.name = request.getName();
        this.imageUrls = request.getImageUrls();
        this.category = request.getCategory();

        this.openingTime = LocalTime.parse(request.getOpeningTime(), DateTimeFormatter.ofPattern("HH:mm"));
        this.closingTime = LocalTime.parse(request.getClosingTime(), DateTimeFormatter.ofPattern("HH:mm"));

        this.capacity = request.getCapacity();
        this.size = request.getSize();

        this.pricePerDay = request.getPricePerDay();
        this.discountRate = request.getDiscountRate();
        this.discountPrice = this.pricePerDay * (100 - this.discountRate) / 100;
        this.details = request.getDetails();

        this.city = request.getCity();
        this.district = request.getDistrict();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress() == null ? "" : request.getDetailAddress();

        this.websiteUrl = request.getWebsiteUrl() == null ? "" : request.getWebsiteUrl();
        this.contactNumber = request.getContactNumber();
        this.facilityInfos = request.getFacilityInfos()
                .stream()
                .map(FacilityInfoDto::toEntity)
                .toList();
        this.notices = request.getNotices().stream().toList();

        this.isActivated = request.getIsActivated() == null || request.getIsActivated();
    }

    public void updateLocation(Point point) {
        this.location = point;
    }

    public void addSpaceNearestSubway(SpaceNearestSubway nearestSubway) {
        this.nearestSubway = nearestSubway;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void addSpaceTag(SpaceTag spaceTag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(spaceTag);
    }

    public void removeSpaceTag(SpaceTag spaceTag) {
        tags.remove(spaceTag);
    }
}
