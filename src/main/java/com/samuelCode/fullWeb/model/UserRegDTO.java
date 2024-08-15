package com.samuelCode.fullWeb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDTO {

    private String username;
    private String mobileNum;
    private String email;
    private String password;
    private boolean enabled;
    private Date createdDate;
    private String roleName;

}
