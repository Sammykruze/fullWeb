package com.samuelCode.fullWeb.repository;

import com.samuelCode.fullWeb.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepo extends JpaRepository<UserRole, Long> {
    UserRole findByRoleName(String roleName);
}