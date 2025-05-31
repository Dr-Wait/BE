package com.DrWait.global.security.auth.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String fullname;
    private String phoneNumber;
    private String residentRegistrationNumber;
}
