package com.DrWait.core.security.auth;

import com.DrWait.core.security.auth.dto.LoginRequestDto;
import com.DrWait.core.security.auth.dto.LoginResponseDto;
import com.DrWait.core.security.auth.dto.SignupRequestDto;
import com.DrWait.core.security.token.JwtTokenProvider;
import com.DrWait.core.security.token.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;

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
