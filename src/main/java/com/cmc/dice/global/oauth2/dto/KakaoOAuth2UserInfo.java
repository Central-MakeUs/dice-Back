package com.cmc.dice.global.oauth2.dto;

import com.cmc.dice.global.oauth2.dto.common.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map kakaoAccount = (Map) attributes.get("kakao_account");
        return ((Map) kakaoAccount.get("profile")).get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return ((Map) attributes.get("kakao_account")).get("email").toString();
    }
}
