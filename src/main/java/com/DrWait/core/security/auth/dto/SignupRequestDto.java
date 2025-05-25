package com.DrWait.core.security.auth.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String phoneNumber;
    private String residentRegistrationNumber;
}
