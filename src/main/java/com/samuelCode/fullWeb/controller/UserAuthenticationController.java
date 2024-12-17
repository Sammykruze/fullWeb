package com.samuelCode.fullWeb.controller;

import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.LoginRequest;
import com.samuelCode.fullWeb.model.UserRegDTO;
import com.samuelCode.fullWeb.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class UserAuthenticationController {

    private final UserAuthenticationService authenticationServiceService;


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegDTO userRegDTO) {
        return ResponseEntity.ok(authenticationServiceService.register(userRegDTO));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationServiceService.authenticateUser(loginRequest));
    }
}
