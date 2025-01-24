package com.cmc.dice.domain.user.application;

import com.cmc.dice.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {
    private final UserRepository userRepository;

}