package com.cmc.dice.domain.user.application;

import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.HostInfoDto;
import com.cmc.dice.domain.user.dto.UpdateHostInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {
    private final PasswordEncoder passwordEncoder;
    
    private final UserRepository userRepository;

    /**
     * 호스트 마이페이지 조회
     */
    public HostInfoDto getHostInfo(User user) {
        return HostInfoDto.of(user);
    }

    /**
     * 호스트 마이페이지 수정
     */
    public HostInfoDto updateHostInfo(User user, UpdateHostInfoRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user.updateHostInfo(request);
        userRepository.save(user);

        return HostInfoDto.of(user);
    }
}