package com.DrWait.global.security.auth.controller;

import com.DrWait.global.security.auth.dto.LoginRequestDto;
import com.DrWait.global.security.auth.dto.LoginResponseDto;
import com.DrWait.global.security.auth.dto.SignupRequestDto;
import com.DrWait.global.security.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto request){
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue("RefreshToken") String bearerRefreshToken){
        return ResponseEntity.ok(authService.reissue(bearerRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerAccessToken){
        authService.logout(bearerAccessToken);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
