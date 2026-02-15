package com.cmc.dice.global.jwt.filter;

import com.cmc.dice.global.jwt.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cmc.dice.domain.user.application.UserDetailsServiceImpl;
import com.cmc.dice.global.exception.ApiErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String GUEST_EMAIL = "guest";
    private static final String[] GUEST_ALLOWED_PATHS = {
            "/api/v2/space/list",
            "/api/v2/space/*",
            "/api/v1/announcement/list",
            "/api/v1/announcement/*"
    };
    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AntPathMatcher pathMatcher;

    public JwtAuthorizationFilter(TokenService tokenService, UserDetailsServiceImpl userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.pathMatcher = new AntPathMatcher();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = parseHeader(request);

        if (StringUtils.hasText(token)) {
            String email = tokenService.validateAccessToken(token);

            if (email == null) {
                jwtExceptionHandler(response);
                return;
            }
            else if (GUEST_EMAIL.equals(email)) {
                String uri = request.getRequestURI();
                boolean isAllowed = Arrays.stream(GUEST_ALLOWED_PATHS)
                        .anyMatch(pattern -> pathMatcher.match(pattern, uri));
                if (!isAllowed) {
                    jwtExceptionHandler(response);
                    return;
                }
            }

            setAuthentication(email);
        }

        filterChain.doFilter(request, response);
    }

    private String parseHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void jwtExceptionHandler(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new ObjectMapper()
                .writeValueAsString(ApiErrorResponse.of(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."));
        response.getWriter().write(json);
    }

}
