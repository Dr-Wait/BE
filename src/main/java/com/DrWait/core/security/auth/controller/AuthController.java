package com.DrWait.core.security.auth.controller;

import com.DrWait.core.security.auth.dto.HospitalSignupRequestDto;
import com.DrWait.core.security.auth.dto.LoginRequestDto;
import com.DrWait.core.security.auth.dto.LoginResponseDto;
import com.DrWait.core.security.auth.sesrvice.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody HospitalSignupRequestDto request){
        authService.hospitalSignup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.hospitalLogin(request));
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
