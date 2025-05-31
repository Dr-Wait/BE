package com.DrWait.domain.user.controller;

import com.DrWait.domain.user.dto.UserInfoResponseDto;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.service.UserService;
import com.DrWait.global.security.auth.service.AuthService;
import com.DrWait.global.security.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/info")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @GetMapping("")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if(token == null || !jwtTokenProvider.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = authService.getUserByBearerToken(token);
        return ResponseEntity.ok(userService.getUserByUserId(user));
    }
}
