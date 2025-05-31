package com.DrWait.domain.user.service;

import com.DrWait.domain.user.dto.UserInfoResponseDto;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.repository.UserRepository;
import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
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

    public User getUserEntityByUserId(String userId){
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserEntityByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public UserInfoResponseDto getUserByUserId(User user){
        return new UserInfoResponseDto(getUserEntityByUserId(user.getId().toString()));
    }
}