package com.cmc.dice.domain.space.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Facility {
    CCTV("cctv"),
    CHAIR("chair"),
    CIRCLE_TABLE("circle_table"),
    COUCH("couch"),
    DESKTOP("desktop"),
    DRINK("drink"),
    FIRE_EXTINGUISHER("fire_extinguisher"),
    FIRE_ALARM("fire_alarm"),
    FIRST_AID_KIT("first_aid_kit"),
    LIGHT("light"),
    MONITOR("monitor"),
    PRINTER("printer"),
    PROJECTOR("projector"),
    SHELF("shelf"),
    SPEAKER("speaker"),
    SQUARE_TABLE("square_table"),
    STANDING_TABLE("standing_table"),
    TV("tv"),
    WATER_PURIFIER("water_purifier"),
    WIFI("wifi");

    private final String value;
}
