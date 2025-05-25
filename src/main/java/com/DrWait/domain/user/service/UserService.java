package com.DrWait.domain.user.service;

import com.DrWait.domain.user.dto.UserInfoResponseDto;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponseDto getUserInfo(String userId){
        log.info("서치 시작");

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new UserInfoResponseDto(user);
    }
}
