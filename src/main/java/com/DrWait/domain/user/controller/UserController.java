package com.DrWait.domain.user.controller;

import com.DrWait.domain.user.dto.UserInfoResponseDto;
import com.DrWait.domain.user.service.UserPrincipal;
import com.DrWait.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/info")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal UserPrincipal user){
        return ResponseEntity.ok(userService.getUserInfo(user.getUsername()));
    }
}
