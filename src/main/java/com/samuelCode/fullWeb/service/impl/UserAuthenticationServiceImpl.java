package com.samuelCode.fullWeb.service.impl;

import com.samuelCode.fullWeb.entity.UserReg;
import com.samuelCode.fullWeb.entity.UserRole;
import com.samuelCode.fullWeb.exception.ErrorMessages;
import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.LoginRequest;
import com.samuelCode.fullWeb.model.UserRegDTO;
import com.samuelCode.fullWeb.repository.RoleRepo;
import com.samuelCode.fullWeb.repository.UserAuthenticationRepo;
import com.samuelCode.fullWeb.config.TokenAuthenticationService;
import com.samuelCode.fullWeb.service.UserAuthenticationService;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserAuthenticationRepo authDAO;

    private final RoleRepo roleRepo;

    private final TokenAuthenticationService tokenAuthenticationService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;



    @Override
     public AuthResponse register(UserRegDTO userRegDTO) {
        try {
            if (authDAO.findByUsername(userRegDTO.getUsername()).isPresent()) {
                throw new Exception("Username already exists with Id: " + userRegDTO.getUsername());
            } else if (authDAO.findByEmail(userRegDTO.getEmail()).isPresent()) {
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

            String accessToken = tokenAuthenticationService.generate(userReg1, "ACCESS");

            return new AuthResponse(accessToken);

        }catch (Exception ex){
            logger.info(ex.getMessage());
            String errorLog = String.format("%s: %s", ErrorMessages.COULD_NOT_CREATE_USER.getErrorMessage(),
                    ex.getMessage());
            throw new ServiceException(errorLog);
        }
    }

    //another implementation that can be used to register user
    public AuthResponse register1(UserRegDTO userRegDTO) {
       var user = UserReg.builder()
               .username(userRegDTO.getUsername())
               .mobileNum(userRegDTO.getMobileNum())
               .password(passwordEncoder.encode(userRegDTO.getPassword()))
               //implement for role .role(roleRepo.findByRoleName(userRegDTO.getRoleName())
               .build();
        authDAO.save(user);
        var jwtToken = tokenAuthenticationService.generate(user, "ACCESS");
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = authDAO.findByEmail(loginRequest.getUserEmail());
            var jwtToken = tokenAuthenticationService.generate(new UserReg(), "ACCESS");
            return AuthResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } catch (Exception ex){
            logger.info(ex.getMessage());
            String errorLog = String.format("%s: %s", ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage(),
                    ex.getMessage());
            throw new ServiceException(errorLog);
        }

    }

    @Override
    public boolean recordExist(UserRegDTO userRegDTO) {
        return false;
    }
}
