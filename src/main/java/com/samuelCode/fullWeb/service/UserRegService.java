package com.samuelCode.fullWeb.service;

import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.LoginRequest;
import com.samuelCode.fullWeb.model.LoginResponse;
import com.samuelCode.fullWeb.model.UserRegDTO;

public interface UserRegService {
    public AuthResponse createUser(UserRegDTO userRegDTO);

    public LoginResponse authLogin(LoginRequest log);

    public boolean recordExist(UserRegDTO userRegDTO);
}
