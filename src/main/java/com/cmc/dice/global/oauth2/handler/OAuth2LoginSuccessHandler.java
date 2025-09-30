package com.cmc.dice.global.oauth2.handler;

import com.cmc.dice.global.jwt.TokenService;
import com.cmc.dice.global.jwt.dto.TokenDto;
import com.cmc.dice.global.oauth2.dto.common.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.front-uri}")
    private String frontURI;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2Member = (CustomOAuth2User) authentication.getPrincipal();
//            loginSuccess(response, oAuth2Member); // 로그인에 성공한 경우 access, refresh 토큰 생성

            String email = oAuth2Member.getEmail();
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

            String access = tokenService.createAccessToken(email);
            String refresh = tokenService.createRefreshToken(email);
            response.sendRedirect(frontURI
                    + "/oauth2/code/" + oauth2Token.getAuthorizedClientRegistrationId()
                    + "?accessToken=" + access
                    + "&refreshToken=" + refresh);
        } catch (Exception e) {
            throw e;
        }
    }


    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2Member) throws IOException {
        String accessToken = tokenService.createAccessToken(oAuth2Member.getEmail());
        String refreshToken = tokenService.createRefreshToken(oAuth2Member.getEmail());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new TokenDto(accessToken, refreshToken)));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
