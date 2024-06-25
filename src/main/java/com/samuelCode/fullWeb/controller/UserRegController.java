package com.samuelCode.fullWeb.controller;

import com.samuelCode.fullWeb.model.AuthResponse;
import com.samuelCode.fullWeb.model.UserRegDTO;
import com.samuelCode.fullWeb.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
//@Api(value = "USERAPI", tags = { "USERAPI" })
public class UserRegController {
    private final UserRegService userRegService;
    @Autowired
    public UserRegController(final UserRegService userRegService) {
        this.userRegService = userRegService;
    }

    //@ApiOperation(value = "User's Creation", tags = { "USE  RAPI" })
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegDTO authRequest) {
        return ResponseEntity.ok(userRegService.createUser(authRequest));
    }
}
