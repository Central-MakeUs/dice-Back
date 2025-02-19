package com.cmc.dice.domain.user.application;

import com.cmc.dice.domain.space.dao.SpaceRepository;
import com.cmc.dice.domain.space.dto.SpaceSimpleInfoDto;
import com.cmc.dice.domain.user.dao.UserRepository;
import com.cmc.dice.domain.user.domain.User;
import com.cmc.dice.domain.user.dto.HostInfoDto;
import com.cmc.dice.domain.user.dto.UpdateHostInfoRequest;
import com.cmc.dice.domain.user.exception.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostService {
    private final PasswordEncoder passwordEncoder;
    
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

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

        try{
            userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidDataException("호스트 정보 수정에 실패했습니다. (중복, 유효하지 않은 값, 권한 없음 등의 이유)");
        }

        return HostInfoDto.of(user);
    }

    /**
     * 호스트의 공간 목록 조회
     */
    public List<SpaceSimpleInfoDto> getHostSpace(User user) {
        return spaceRepository.findByAdmin(user).stream()
                .map(SpaceSimpleInfoDto::of)
                .toList();
    }
}