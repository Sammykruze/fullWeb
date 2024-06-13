package com.samuelCode.fullWeb.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRegDTO {

    private String username;
    private String mobileNum;
    private String email;
    private String password;
    private boolean enabled;
    private Date createdDate;
    private String roleName;

    public UserRegDTO() {
        super();
    }
}
