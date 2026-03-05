package com.cmc.dice.domain.user.dao;

import com.cmc.dice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> findByEmailAndName(String email, String name);

    Optional<User> findByFcmToken(String fcmToken);
}
