package com.samuelCode.fullWeb.service.impl;

import com.samuelCode.fullWeb.entity.UserReg;
import com.samuelCode.fullWeb.entity.UserRole;
import com.samuelCode.fullWeb.exception.ErrorMessages;
import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.LoginRequest;
import com.samuelCode.fullWeb.model.LoginResponse;
import com.samuelCode.fullWeb.model.UserRegDTO;
import com.samuelCode.fullWeb.repository.RoleRepo;
import com.samuelCode.fullWeb.repository.UserRegRepo;
import com.samuelCode.fullWeb.security.TokenAuthenticationService;
import com.samuelCode.fullWeb.service.UserRegService;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Service
public class UserRegServiceImpl implements UserRegService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserRegRepo authDAO;
    @Autowired
    RoleRepo roleRepo;
   @Autowired
   private AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
    @Autowired
    private TokenAuthenticationService jwtUtils;

    @Override
    public AuthResponse createUser(UserRegDTO userRegDTO) {
        try {
            if (authDAO.findByUsername(userRegDTO.getUsername()) != null) {
                throw new Exception("Username already exists with Id: " + userRegDTO.getUsername());
            } else if (authDAO.findByEmail(userRegDTO.getEmail()) != null) {
                throw new Exception("Email is already used " + userRegDTO.getEmail());
            }
            String username = userRegDTO.getUsername();
            String mobileNum = userRegDTO.getMobileNum();
            String email = userRegDTO.getEmail();
            String password = userRegDTO.getPassword();
            String pwd = null;

            if (!StringUtils.isEmpty(password)) {
                pwd = new BCryptPasswordEncoder(11).encode(userRegDTO.getPassword());
            }

            UserRole role = roleRepo.findByRoleName(userRegDTO.getRoleName());
            Collection<UserRole> userRole = new ArrayList<>();
            userRole.add(role);

            UserReg userReg = new UserReg(username, mobileNum, email, pwd, true, new Date(), userRole);
            UserReg userReg1 = authDAO.save(userReg);

            Assert.notNull(userReg1, "Failed to register user. Please try again later");

            String accessToken = jwtUtils.generate(userReg1, "ACCESS");
            String refreshToken = jwtUtils.generate(userReg1, "REFRESH");

            return new AuthResponse(accessToken, refreshToken);

        }catch (Exception ex){
            logger.info(ex.getMessage());
            String errorLog = String.format("%s: %s", ErrorMessages.COULD_NOT_CREATE_USER.getErrorMessage(),
                    ex.getMessage());
            throw new ServiceException(errorLog);
        }
    }

    @Override
    public LoginResponse authLogin(LoginRequest log) {
        return null;
    }

    @Override
    public boolean recordExist(UserRegDTO userRegDTO) {
        return false;
    }
}
