package com.DrWait.domain.user.repository;

import com.DrWait.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String mail);

    // 존재 여부만 확인하는 용도
    boolean existsByEmail(String email);
}
