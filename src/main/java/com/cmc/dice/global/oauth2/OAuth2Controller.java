package com.cmc.dice.global.oauth2;

import com.cmc.dice.global.oauth2.dto.common.SocialLoginUrlResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "OAuth2")
@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {
    @GetMapping("/{socialType}")
    public ResponseEntity<SocialLoginUrlResponse> socialLoginRedirect (
            @PathVariable(name = "socialType") String socialType,
            HttpServletResponse response) throws IOException {
        String socialLoginUrl = "https://diceminipop.site/oauth2/authorization/" + socialType;
//        response.sendRedirect("http://localhost:8080/oauth2/authorization/" + socialType);
        return ResponseEntity.ok(new SocialLoginUrlResponse(socialLoginUrl));
    }
}
