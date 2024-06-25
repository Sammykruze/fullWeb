package com.samuelCode.fullWeb.repository;

import com.samuelCode.fullWeb.entity.UserReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegRepo extends JpaRepository<UserReg, Long> {
    UserReg findByUsername(String username);

    UserReg findByEmail(String email);
}
