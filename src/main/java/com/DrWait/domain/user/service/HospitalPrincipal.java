package com.DrWait.domain.user.service;

import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.user.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class HospitalPrincipal implements UserDetails {

    private final UUID hospitalId;
    private final String role;

    public HospitalPrincipal(Hospital hospital) {
        this.hospitalId = hospital.getId();
        this.role = Role.ADMIN.name();
    }

    public static HospitalPrincipal from(Hospital hospital) {
        return new HospitalPrincipal(hospital);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return hospitalId.toString(); // 토큰 검증 후 ID를 기준으로 불러올 때 사용
    }

    // 계정 상태 검사
    @Override
    public String getPassword() { return " "; }
    @Override public boolean isAccountNonExpired() { return UserDetails.super.isAccountNonExpired(); }
    @Override public boolean isAccountNonLocked() { return UserDetails.super.isAccountNonLocked(); }
    @Override public boolean isCredentialsNonExpired() { return UserDetails.super.isCredentialsNonExpired(); }
    @Override public boolean isEnabled() { return UserDetails.super.isEnabled(); }
}