package com.DrWait.global.security.auth.service;

import com.DrWait.global.error.CustomException;
import com.DrWait.global.error.ErrorCode;
import com.DrWait.global.security.auth.dto.HospitalSignupRequestDto;
import com.DrWait.global.security.auth.dto.LoginRequestDto;
import com.DrWait.global.security.auth.dto.LoginResponseDto;
import com.DrWait.global.security.auth.dto.SignupRequestDto;
import com.DrWait.global.security.token.JwtTokenProvider;
import com.DrWait.global.security.token.RefreshTokenStore;
import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.repository.HospitalRepository;
import com.DrWait.domain.user.entity.User;
import com.DrWait.global.security.auth.enums.Role;
import com.DrWait.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final RefreshTokenStore refreshTokenStore;
    private final PasswordEncoder passwordEncoder;

    public User getUserByBearerToken(String bearerToken){
        return userRepository.findById(UUID.fromString(jwtTokenProvider.getUserId(bearerToken)))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public Hospital getHospitalByBearerToken(String bearerToken){
        return hospitalRepository.findById(UUID.fromString(jwtTokenProvider.getUserId(bearerToken)))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void signup(SignupRequestDto request){
        // 1. 사용자 아이디 중복 체크
        if(userRepository.existsByUsername(request.getUsername())){
            throw new CustomException(ErrorCode.ALREADY_EXISTED_USERNAME);
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .fullname(request.getFullname())
                .phoneNumber(request.getPhoneNumber())
                .residentRegistrationNumber(request.getResidentRegistrationNumber())
//                .profileImageUrl("기본 이미지 URL")
                .build();

        userRepository.save(user);
    }

    public void hospitalSignup(HospitalSignupRequestDto request){
        // 1. 사용자 아이디 중복체크
        if(hospitalRepository.existsByUsername(request.getUsername())){
            throw new CustomException(ErrorCode.ALREADY_EXISTED_USERNAME);
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(encodedPassword)
                .department(request.getDepartment())
                .address(request.getAddress())
                .telephone(request.getTelephone())
                .websiteUrl(request.getWebsite_url())
                .build();

        hospitalRepository.save(hospital);
    }

    public LoginResponseDto login(LoginRequestDto request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // TODO: Role 를 DB에 저장할지 고민
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId().toString(), Role.USER.name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString(), Role.USER.name());

        refreshTokenStore.save(user.getId().toString(), refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public LoginResponseDto hospitalLogin(LoginRequestDto request){
        Hospital hospital = hospitalRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), hospital.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // TODO: Role 를 DB에 저장할지 고민
        String accessToken = jwtTokenProvider.generateAccessToken(hospital.getId().toString(), Role.ADMIN.name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(hospital.getId().toString(), Role.ADMIN.name());

        refreshTokenStore.save(hospital.getId().toString(), refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public boolean isExistUsername (String username){
         Optional<User> user = userRepository.findByUsername(username);

        return user.isPresent();
    }

    public boolean isExistHospitalUsername (String username){
        Optional<Hospital> hospital = hospitalRepository.findByUsername(username);

        return hospital.isPresent();
    }

    public LoginResponseDto reissue(String bearerRefreshToken){
        String refreshToken = bearerRefreshToken.replace("Bearer", "").trim();
        String userId = jwtTokenProvider.getUserId(refreshToken);

        String savedToken = refreshTokenStore.get(userId);

        if(savedToken == null){
            throw new CustomException(ErrorCode.ALREADY_LOGOUT);
        }
        if(!refreshToken.equals(savedToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESHTOKEN);
        }
        refreshTokenStore.remove(userId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, Role.USER.name());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId, Role.USER.name());
        refreshTokenStore.save(userId, newRefreshToken);

        return new LoginResponseDto(newAccessToken, refreshToken);
    }

    public void logout(String token){
        refreshTokenStore.remove(jwtTokenProvider.getUserId(token));
    }
}
