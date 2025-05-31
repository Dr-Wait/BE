package com.DrWait.global.security.auth.controller;

import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import com.DrWait.global.security.auth.dto.HospitalSignupRequestDto;
import com.DrWait.global.security.auth.dto.LoginRequestDto;
import com.DrWait.global.security.auth.dto.LoginResponseDto;
import com.DrWait.global.security.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/hospital")
public class HospitalAuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody HospitalSignupRequestDto request){
        authService.hospitalSignup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/signup/{username}")
    public ResponseEntity<?> isExistedHospitalUsername(@PathVariable("username") String username){
        if(authService.isExistHospitalUsername(username)) throw new CustomException(ErrorCode.ALREADY_EXISTED_USERNAME);
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.hospitalLogin(request));
    }
}
