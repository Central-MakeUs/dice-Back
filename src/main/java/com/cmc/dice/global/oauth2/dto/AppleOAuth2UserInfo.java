package com.cmc.dice.global.oauth2.dto;


import com.cmc.dice.global.oauth2.dto.common.OAuth2UserInfo;

import java.util.Map;

public class AppleOAuth2UserInfo extends OAuth2UserInfo {
    public AppleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }
    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
