package com.cmc.dice.domain.space.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Facility {
    CCTV("CCTV"),
    CHAIR("의자"),
    CIRCLE_TABLE("원형 테이블"),
    COUCH("쇼파"),
    DESKTOP("데스크탑"),
    DRINK("음료수 보관대"),
    FIRE_EXTINGUISHER("소화기"),
    FIRE_ALARM("화재 경보기"),
    FIRST_AID_KIT("구급 상자"),
    LIGHT("조명"),
    MONITOR("모니터"),
    PRINTER("프린터"),
    PROJECTOR("빔 프로젝터"),
    SHELF("진열대"),
    SPEAKER("스피커"),
    SQUARE_TABLE("사각 테이블"),
    STANDING_TABLE("스탠딩 테이블"),
    TV("TV"),
    WATER_PURIFIER("정수기"),
    WIFI("Wi-Fi");

    private final String value;
}
