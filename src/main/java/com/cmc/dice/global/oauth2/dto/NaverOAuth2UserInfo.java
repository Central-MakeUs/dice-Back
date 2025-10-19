package com.cmc.dice.global.oauth2.dto;

import com.cmc.dice.global.oauth2.dto.common.OAuth2UserInfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return (String) ((Map) attributes.get("response")).get("id");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("name");
    }
}
