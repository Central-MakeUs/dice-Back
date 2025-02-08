package com.cmc.dice.domain.space.domain;


import com.cmc.dice.domain.space.dto.CreateSpaceRequest;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spaces")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Space extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User admin; // 공간 소유자

    @Column(nullable = false)
    private String name; // 공간 이름

    @Column(nullable = false, length = 1000)
    private String description; // 공간 한줄 소개

    @Convert(converter = ImageUrlListConverter.class)
    @Column(columnDefinition = "TEXT") // MySQL에서 JSON 타입 또는 TEXT 타입으로 저장
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceCategory category; // 공간 카테고리 (예: 갤러리, 카페 등)

    private LocalTime openingTime; // 공간 운영 시작 시간
    private LocalTime closingTime; // 공간 운영 마감 시간

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

    private String websiteUrl; // 웹사이트 URL
    private String contactNumber; // 연락처

    // 시설 이용 및 공지사항 안내 작성
    @Lob
    private String facilityInfo; // 시설 이용 안내
    @Lob
    private String notice; // 공지사항

    @Builder.Default
    private int likeCount = 0; // 좋아요 수

    public Space(User user, CreateSpaceRequest request) {
        this.admin = user;
        this.name = request.getName();
        this.description = request.getDescription();
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

        this.websiteUrl = request.getWebsiteUrl();
        this.contactNumber = request.getContactNumber();
        this.facilityInfo = request.getFacilityInfo();
        this.notice = request.getNotice();
    }

    public boolean isOwner(User user) {
        return this.admin.equals(user);
    }

    public void update(CreateSpaceRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
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

        this.websiteUrl = request.getWebsiteUrl();
        this.contactNumber = request.getContactNumber();
        this.facilityInfo = request.getFacilityInfo();
        this.notice = request.getNotice();
    }

    public void updateLocation(Point point) {
        this.location = point;
    }
}