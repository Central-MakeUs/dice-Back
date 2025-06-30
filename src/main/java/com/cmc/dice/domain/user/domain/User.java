package com.cmc.dice.domain.user.domain;

import com.cmc.dice.domain.user.dto.CreateUserRequestV1;
import com.cmc.dice.domain.user.dto.CreateUserRequestV2;
import com.cmc.dice.domain.user.dto.UpdateGuestInfoRequest;
import com.cmc.dice.domain.user.dto.UpdateHostInfoRequest;
import com.cmc.dice.global.entity.BaseEntity;
import com.cmc.dice.global.oauth2.dto.common.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "social_type"})
        }
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    private String phone;

    private String bankName;

    private String accountNumber;

    private String fcmToken;

    private SocialType socialType;

    private boolean alarmOn = false;

    // 이메일로 회원가입
    public User(CreateUserRequestV1 dto, String encodedPassword) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.password = encodedPassword;
        this.phone = dto.getPhone();
        this.userRole = UserRole.USER;
    }

    public User(CreateUserRequestV2 dto, String encodedPassword) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.password = encodedPassword;
        this.phone = dto.getPhone();
        this.userRole = dto.getUserRole();
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }

    public void updateGuestInfo(UpdateGuestInfoRequest request) {
        this.name = request.getName();
        this.phone = request.getPhone();
        this.email = request.getEmail();
    }
    public void updateHostInfo(UpdateHostInfoRequest request) {
        this.name = request.getName();
        this.phone = request.getPhone();
        this.bankName = request.getBankName();
        this.accountNumber = request.getAccountNumber();
    }

    public User updateSocialInfo(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }
}
