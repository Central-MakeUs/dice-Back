package com.cmc.dice.domain.space.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Facility {
    CCTV("cctv"),
    CHAIR("chair"),
    CIRCLE_TABLE("circleTable"),
    COUCH("couch"),
    DESKTOP("desktop"),
    DRINK("drink"),
    FIRE_EXTINGUISHER("fireExtinguisher"),
    FIRE_ALARM("fire_alarm"),
    FIRST_AID_KIT("firstAidKit"),
    LIGHT("light"),
    MONITOR("monitor"),
    PRINTER("printer"),
    PROJECTOR("projector"),
    SHELF("shelf"),
    SPEAKER("speaker"),
    SQUARE_TABLE("squareTable"),
    STANDING_TABLE("standingTable"),
    TV("tv"),
    WATER_PURIFIER("waterPurifier"),
    WIFI("wifi");

    private final String value;

    @JsonCreator
    public static Facility fromValue(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Facility.values())
                .filter(facility -> facility.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown facility value: " + value));
    }
}
