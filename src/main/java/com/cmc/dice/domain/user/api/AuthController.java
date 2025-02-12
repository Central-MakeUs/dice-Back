package com.cmc.dice.domain.user.api;

import com.cmc.dice.domain.user.application.AuthService;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.*;
import com.cmc.dice.global.exception.ApiErrorResponse;
import com.cmc.dice.global.jwt.CurrentUser;
import com.cmc.dice.global.jwt.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/validate/email")
    @Operation(summary = "이메일 중복 확인", description = """
            # 이메일 중복 확인
                        
            사용자의 이메일 중복을 확인합니다.
                        
            ## 응답
                        
            - 중복된 이메일이 있을 경우 `409` 에러를 반환합니다.
            - 중복된 이메일이 없을 경우 `200` 코드를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "409",
            description = "입력한 이메일이 이미 존재합니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = "{\n  \"status\": \"CONFLICT\",\n  \"message\": \"입력한 이메일이 이미 존재합니다.\"\n}")
            )
    )
    public void validateDuplicateEmail(@Valid @RequestBody EmailValidateDto email) {
        authService.validateDuplicateEmail(email);
    }

    @PostMapping("/validate/phone")
    @Operation(summary = "휴대폰 번호 중복 확인", description = """
            # 휴대폰 번호 중복 확인
                        
            사용자의 휴대폰 번호 중복을 확인합니다.
                        
            ## 응답
                        
            - 중복된 휴대폰 번호가 있을 경우 `409` 에러를 반환합니다.
            - 중복된 휴대폰 번호가 없을 경우 `200` 코드를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "409",
            description = "입력한 휴대폰 번호가 이미 존재합니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = "{\n  \"status\": \"CONFLICT\",\n  \"message\": \"입력한 휴대폰 번호가 이미 존재합니다.\"\n}")
            )
    )
    public void validateDuplicatePhone(@Valid @RequestBody PhoneValidateDto phone) {
        authService.validateDuplicatePhone(phone);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = """
            # 회원가입
                        
            회원을 생성합니다.
            각 필드의 제약 조건은 다음과 같습니다.

            | 필드명 | 설명 | 제약조건 | 중복확인 | 예시 |
            |--------|------|----------|----------|------|
            | email | 사용자의 이메일 | 이메일 형식 | Y | email01@email.com |
            | name | 사용자의 이름 | 2~20자 | N | name01 |
            | password | 사용자의 비밀번호 | 영문(대소문자), 숫자, 특수문자를 포함한 8~32자 | N | password01! |
            | phone | 사용자의 휴대폰 번호 | 숫자 11자 | Y | 01012345678 |
            
                         
            ## 응답
                        
            - 회원 가입 성공 시 `200` 코드와 함께 회원 기본 정보를 반환합니다.
            - 입력 양식에 오류가 있을 경우 `400` 에러를 반환합니다.
            - 중복된 값이 있을 경우 `409` 에러를 반환합니다.
             
            """)
    @ApiResponse(
            responseCode = "200",
            description = "생성한 계정 고유 번호를 반환합니다.",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "409",
            description = "입력 값 중 중복된 값이 있습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = "{\n  \"status\": \"CONFLICT\",\n  \"message\": \"데이터 중복\"\n}")
            )
    )
    public UserAuthInfoDto createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return authService.createUser(createUserRequest);
    }


    @PostMapping("/login")
    @Operation(summary = "이메일 로그인", description = """
            # 로그인
                        
            사용자의 이메일과 비밀번호를 입력하여 로그인합니다.
                        
            ## 응답
                        
            - 로그인 성공 시 `200` 코드와 함께 유저 정보 및 토큰을 반환합니다.
              - 유저 정보엔는 email, name, role 이 포함되어 있습니다.
              - 토큰은 `accessToken`과 `refreshToken`으로 구성되어 있습니다.
            - 로그인 실패 시 `400` 에러를 반환합니다.
              - 계정이 존재하지 않거나 비밀번호가 일치하지 않을 경우 발생합니다.     
            """)
    @ApiResponse(
            responseCode = "200",
            description = "로그인 성공 시 유저 정보와 함께 토큰을 반환합니다.",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "400",
            description = "로그인 실패",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = """
                            {
                                "status": "BAD_REQUEST",
                                "message": "로그인에 실패했습니다."
                            }
                            """)
            )
    )
    public LoginResponseDto login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/password-reset/request")
    @Operation(summary = "비밀번호 재설정 이메일 전송", description = """
            # 비밀번호 재설정 이메일 전송
                        
            사용자의 이메일로 비밀번호 재설정 이메일을 전송합니다.
                        
            ## 응답
                        
            - 이메일 전송 성공 시 `200` 코드를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "404",
            description = "해당 유저 정보를 찾을 수 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = """
                            {
                                "status": "NOT_FOUND",
                                "message": "해당 유저 정보를 찾을 수 없습니다."
                            }
                            """)
            )
    )
    public void sendPasswordResetEmail(@Valid @RequestBody PasswrodResetValidateDto passwrodResetValidateDto) {
        authService.sendPasswordResetEmail(passwrodResetValidateDto);
    }

    @PostMapping("/password-reset/reset")
    @Operation(summary = "비밀번호 재설정", description = """
            # 비밀번호 재설정
                        
            사용자의 비밀번호를 재설정합니다.
            사용자의 이름과 이메일을 검사해 일치하는 사용자의 임시 비밀번호를 설정합니다.
                        
            ## 응답
                        
            - 비밀번호 재설정 성공 시 `200` 코드를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "404",
            description = "해당 유저 정보를 찾을 수 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = """
                            {
                                "status": "NOT_FOUND",
                                "message": "해당 유저 정보를 찾을 수 없습니다."
                            }
                            """)
            )
    )
    public PasswordResetDto resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        return authService.resetPassword(passwordResetRequest);
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = """
            # 토큰 재발급
                        
            리프레시 토큰을 이용하여 액세스 토큰을 재발급합니다.
                        
            ## 응답
                        
            - 토큰 재발급 성공 시 `200` 코드와 함께 새로운 액세스 토큰을 반환합니다.
            - 리프레시 토큰이 만료되었을 경우 `400` 에러를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            description = "토큰 재발급 성공",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "400",
            description = "토큰 재발급 실패",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    examples = @ExampleObject(value = """
                            {
                                "status": "BAD_REQUEST",
                                "message": "토큰 재발급에 실패했습니다."
                            }
                            """)
            )
    )
    public TokenDto reissue(@Valid @RequestBody ReissueRequest reissueRequest) {
        return authService.reissue(reissueRequest);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = """
            # 로그아웃
                        
            사용자를 로그아웃합니다.
                        
            ## 응답
                        
            - 로그아웃 성공 시 `200` 코드를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            description = "로그아웃 성공",
            useReturnTypeSchema = true
    )
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "access-token")
    public void logout(@CurrentUser User user) {
        authService.logout(user);
    }
}