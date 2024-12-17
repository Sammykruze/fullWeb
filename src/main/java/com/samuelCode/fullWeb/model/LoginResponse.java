package com.samuelCode.fullWeb.model;

import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String username;

    public LoginResponse() {
        super();
    }

    public LoginResponse(String accessToken, String refreshToken, String username) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
