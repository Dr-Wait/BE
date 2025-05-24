package com.DrWait.core.security.auth;

import com.DrWait.core.security.auth.dto.LoginRequestDto;
import com.DrWait.core.security.auth.dto.LoginResponseDto;
import com.DrWait.core.security.auth.dto.SignupRequestDto;
import com.DrWait.core.security.token.JwtTokenProvider;
import com.DrWait.core.security.token.RefreshTokenStore;
import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.enums.Role;
import com.DrWait.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenStore refreshTokenStore;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto request){
        // 1. 이메일 중복 체크
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .residentRegistrationNumber(request.getResidentRegistrationNumber())
//                .profileImageUrl("기본 이미지 URL")
                .build();

        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // TODO: Role 를 DB에 저장할지 고민
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId().toString(), Role.USER.name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString(), Role.USER.name());

        refreshTokenStore.save(user.getId().toString(), refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public LoginResponseDto reissue(String bearerRefreshToken){
        String refreshToken = bearerRefreshToken.replace("Bearer", "").trim();
        String userId = jwtTokenProvider.getUserId(refreshToken);

        String savedToken = refreshTokenStore.get(userId);

        if(savedToken == null){
            throw new IllegalArgumentException("로그아웃된 사용자입니다.");
        }
        if(!refreshToken.equals(savedToken)){
            throw new IllegalArgumentException("유효하지 않은 refreshToken");
        }
        refreshTokenStore.remove(userId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, Role.USER.name());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId, Role.USER.name());
        refreshTokenStore.save(userId, newRefreshToken);

        return new LoginResponseDto(newAccessToken, refreshToken);
    }

    public void logout(String bearerToken){
        String refreshToken = bearerToken.replace("Bearer", "").trim();
        String userId = jwtTokenProvider.getUserId(refreshToken);
        refreshTokenStore.remove(userId);
    }
}
