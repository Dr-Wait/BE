package com.DrWait.domain.user.service;

import com.DrWait.domain.user.entity.User;
import com.DrWait.domain.user.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private final UUID userId;
    private final String password;
    private final Role role;

    public UserPrincipal(User user){
        this.userId = user.getId();
        this.password = user.getPassword();
        this.role = Role.USER;
        // 필요 시 List.of(new SimpleGrantedAuthority("ROLE_USER"))로 권한 추가 가능
    }

    public static UserPrincipal from(User user){
        return new UserPrincipal(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 상태 검사
    @Override public boolean isAccountNonExpired() { return UserDetails.super.isAccountNonExpired(); }
    @Override public boolean isAccountNonLocked() { return UserDetails.super.isAccountNonLocked(); }
    @Override public boolean isCredentialsNonExpired() { return UserDetails.super.isCredentialsNonExpired(); }
    @Override public boolean isEnabled() { return UserDetails.super.isEnabled(); }
}
