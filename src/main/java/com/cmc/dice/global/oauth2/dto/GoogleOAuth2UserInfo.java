package com.cmc.dice.global.oauth2.dto;

import com.cmc.dice.global.oauth2.dto.common.OAuth2UserInfo;

import java.util.Map;

// 구글에서 가져온 정보들을 취합해둔 곳
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
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

