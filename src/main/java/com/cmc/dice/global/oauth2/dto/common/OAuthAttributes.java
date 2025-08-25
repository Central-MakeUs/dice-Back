package com.cmc.dice.global.oauth2.dto.common;

import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.domain.UserRole;
import com.cmc.dice.global.oauth2.dto.AppleOAuth2UserInfo;
import com.cmc.dice.global.oauth2.dto.GoogleOAuth2UserInfo;
import com.cmc.dice.global.oauth2.dto.KakaoOAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

// OAuth2의 종류에 따라서 객체를 google의 정보에 혹은 apple의 정보에 맞도록 객체화시킴.
@Getter
public class OAuthAttributes {
    private OAuth2UserInfo oAuth2UserInfo;// oauth 로그인시 받은 정보들을 그대로 저장함.
    private String nameAttributeKey; // OAuth2 로그인시, 키가 되는 필드.(=pk)

    @Builder
    public OAuthAttributes(OAuth2UserInfo oAuth2UserInfo,
                           String nameAttributeKey) {
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.nameAttributeKey = nameAttributeKey;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map
    // 따라서 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        switch (socialType) {
            case GOOGLE:
                return ofGoogle(userNameAttributeName, attributes);
            case APPLE:
                return ofApple(userNameAttributeName, attributes);
            case NAVER:
                return ofNaver(userNameAttributeName, attributes);
            case KAKAO:
                return ofKakao(userNameAttributeName, attributes);
            default:
                throw new IllegalArgumentException("소셜로그인 실패 (소셜로그인 타입 구분 실패) : " + socialType);
        }
    }

    // 구글 생성자
    private static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofNaver(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofApple(String usernameAttributeName,
                                           Map<String, Object> attributes) {
        // apple은 따로 nameAttributeKey값을 구현해줘야함. google은 기본제공?
        return OAuthAttributes.builder()
                .nameAttributeKey("sub")
                .oAuth2UserInfo(new AppleOAuth2UserInfo(attributes))
                .build();
    }

    // Member 엔티티 생성
    public User toUser(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return User.builder()
                .email(oAuth2UserInfo.getEmail())
                .name(Optional.ofNullable(oAuth2UserInfo.getName()).orElse(null))
                .userRole(UserRole.USER)
                .socialType(socialType)
                .build();
    }
}
