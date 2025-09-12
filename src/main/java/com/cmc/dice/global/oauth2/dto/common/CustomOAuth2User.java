package com.cmc.dice.global.oauth2.dto.common;

import com.cmc.dice.domain.user.domain.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;


/*
* CustomOAuth2Service에서 기본으로 반환되는 객체에 추가할 필드를 위해서 사용함.
* */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String nickName; // 얘 없애도 될지도?
    private String email; // 사용자에게 추가로 받을 정보를 보기 위해서 처리함.
    private UserRole role; // 아직 회원가입 다 완료되지 않은 사용자이면 추가적으로 닉네임값을 받으려고

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String nickName, String email, UserRole role) {
        super(authorities, attributes, Optional.ofNullable(nameAttributeKey).orElse("sub"));
        this.nickName = nickName;
        this.email = email;
        this.role = role;
    }
}
