package com.DrWait.domain.user.service;

import com.DrWait.domain.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final HospitalService hospitalService;

    @Override
    public UserDetails loadUserByUsername(String compositeKey) throws UsernameNotFoundException {
        String[] parts = compositeKey.split(":");
        String type = parts[0];
        String id = parts[1];

        switch (type) {
            case "USER":
                return UserPrincipal.from(userService.getUserEntityByUserId(id));

            case "ADMIN":
                return HospitalPrincipal.from(hospitalService.getHospitalEntityById(id));

            default:
                throw new UsernameNotFoundException("알 수 없는 사용자 유형");
        }
    }

}