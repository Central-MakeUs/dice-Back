package com.cmc.dice.global.oauth2.dto.common;

import com.cmc.dice.domain.user.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String email;
    private String name;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
