package com.DrWait.core.security.auth.dto;

import lombok.Getter;

@Getter
public class HospitalSignupRequestDto {

    private String name;
    private String department;
    private String address;
    private String telephone;
    private String website_url;
    private String username;
    private String password;
}
