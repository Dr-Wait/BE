package com.DrWait.core.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // JSON으로 만들기 위해서 필요
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
}
