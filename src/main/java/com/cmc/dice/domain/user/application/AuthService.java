package com.cmc.dice.domain.user.application;

import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.*;
import com.cmc.dice.domain.user.exception.*;
import com.cmc.dice.global.jwt.TokenService;
import com.cmc.dice.global.jwt.dto.TokenDto;
import com.cmc.dice.global.jwt.refreshtoken.RefreshToken;
import com.cmc.dice.global.jwt.refreshtoken.RefreshTokenRepository;
import com.cmc.dice.global.mail.Mail;
import com.cmc.dice.global.mail.MailRepository;
import com.cmc.dice.global.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailRepository mailRepository;

    private final TokenService tokenService;
    private final MailService mailService;

    @Transactional
    public LoginResponseDto login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(LoginFailException::new);

        if (user.getDeletedAt() != null) {
            throw new UserDeletedException();
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new LoginFailException();
        }

        return LoginResponseDto.builder()
                .user(UserAuthInfoDto.fromEntity(user))
                .token(tokenService.generateToken(user.getEmail()))
                .build();
    }

    @Transactional
    public UserAuthInfoDto createUser(CreateUserRequest createUserRequest) {
        try {
            User createdUser = userRepository.save(
                    new User(createUserRequest, passwordEncoder.encode(createUserRequest.getPassword()))
            );

            return UserAuthInfoDto.builder()
                    .email(createdUser.getEmail())
                    .name(createdUser.getName())
                    .userRole(createdUser.getUserRole())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new UserCreateValidationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public TokenDto reissue(ReissueRequest reissueRequest) {
        String email = tokenService.validateRefreshToken(reissueRequest.getRefreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email)
                .orElseThrow(InvalidRefreshTokenException::new);

        if(!refreshToken.getToken().equals(reissueRequest.getRefreshToken())) {
            throw new InvalidRefreshTokenException();
        }

        return new TokenDto(
                tokenService.createAccessToken(email),
                refreshToken.getToken()
        );
    }

    @Transactional
    public void logout(User user) {
        refreshTokenRepository.deleteByEmail(user.getEmail());
    }

    public void validateDuplicateEmail(EmailValidateDto email) {
        if (userRepository.existsByEmail(email.getEmail())) {
            throw new DuplicateEmailException();
        }
    }

    public void validateDuplicatePhone(PhoneValidateDto phone) {
        if (userRepository.existsByPhone(phone.getPhone())) {
            throw new DuplicatePhoneException();
        }
    }

    public void sendPasswordResetEmail(EmailValidateDto emailValidateDto) {
        mailService.joinEmail(emailValidateDto.getEmail());
    }

    //임시 비밀번호 발급
    public TempPasswordDto resetPassword(PasswordResetValidateDto passwordResetRequest) {
        Mail mail = mailRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(NotFoundUserInfoException::new);

        if (!mail.getCode().equals(passwordResetRequest.getCode())) {
            throw new InvalidAuthCodeException();
        }

        if(mail.getUpdatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new AuthCodeExpiredException();
        }

        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        User user = userRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(NotFoundUserInfoException::new);

        user.updatePassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        return new TempPasswordDto(user.getEmail(), tempPassword);
    }

    public void withdraw(User user) {
        user.delete();
        userRepository.save(user);
    }

    public UserAuthInfoDto updatePassword(User user, PasswordUpdateRequest passwordUpdateRequest) {
        if (!passwordEncoder.matches(passwordUpdateRequest.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        user.updatePassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);

        return UserAuthInfoDto.fromEntity(user);
    }

    public VerifyCodeDto verifyCode(PasswordResetValidateDto passwordResetRequest) {
        Mail mail = mailRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(NotFoundUserInfoException::new);

        if (!mail.getCode().equals(passwordResetRequest.getCode())) {
            throw new InvalidAuthCodeException();
        }

        if(mail.getUpdatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new AuthCodeExpiredException();
        }

        return new VerifyCodeDto(true);
    }
}