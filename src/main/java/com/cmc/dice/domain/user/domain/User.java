package com.cmc.dice.domain.user.domain;

import com.cmc.dice.domain.user.dto.CreateUserRequest;
import com.cmc.dice.domain.user.dto.UpdateGuestInfoRequest;
import com.cmc.dice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    @Column(nullable = false)
    private String phone;

    private String bankName;

    private String accountNumber;

    // 이메일로 회원가입
    public User(CreateUserRequest dto, String encodedPassword) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.password = encodedPassword;
        this.phone = dto.getPhone();
        this.userRole = UserRole.USER;
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }

    public void update(UpdateGuestInfoRequest request) {
        this.name = request.getName();
        this.phone = request.getPhone();
        this.bankName = request.getBankName();
        this.accountNumber = request.getAccountNumber();
    }
}