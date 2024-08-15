package com.samuelCode.fullWeb.service;

import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.LoginRequest;
import com.samuelCode.fullWeb.model.UserRegDTO;

public interface UserAuthenticationService {

    AuthResponse register(UserRegDTO userRegDTO);

    AuthResponse authenticateUser(LoginRequest loginRequest);

    public boolean recordExist(UserRegDTO userRegDTO);


}
