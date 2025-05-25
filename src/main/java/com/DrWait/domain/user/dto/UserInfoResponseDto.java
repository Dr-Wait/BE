package com.DrWait.domain.user.dto;

import com.DrWait.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private String phoneNumber;
    private String profileImageUrl;

    public UserInfoResponseDto(User user){
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
