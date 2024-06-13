package com.samuelCode.fullWeb.repository;

import com.samuelCode.fullWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEMail(String eMail);

}
