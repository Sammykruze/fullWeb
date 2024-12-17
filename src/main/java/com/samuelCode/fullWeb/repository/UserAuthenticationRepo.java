package com.samuelCode.fullWeb.repository;

import com.samuelCode.fullWeb.entity.UserReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationRepo extends JpaRepository<UserReg, Long> {
    Optional<UserReg> findByUsername(String username);
    Optional<UserReg> findByEmail(String email);
}
