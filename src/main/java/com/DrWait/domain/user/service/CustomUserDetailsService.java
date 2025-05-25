package com.DrWait.domain.user.service;

import com.DrWait.domain.hospital.entity.Hospital;
import com.DrWait.domain.hospital.repository.HospitalRepository;
import com.DrWait.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import com.DrWait.domain.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public UserDetails loadUserByUsername(String compositeKey) throws UsernameNotFoundException {
        String[] parts = compositeKey.split(":");
        String type = parts[0];
        UUID id = UUID.fromString(parts[1]);

        switch (type) {
            case "USER":
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
                return UserPrincipal.from(user);

            case "ADMIN":
                Hospital hospital = hospitalRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("병원 없음"));
                return HospitalPrincipal.from(hospital);

            default:
                throw new UsernameNotFoundException("알 수 없는 사용자 유형");
        }
    }

}