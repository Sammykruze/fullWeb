package com.samuelCode.fullWeb.service;

import com.samuelCode.fullWeb.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(User user);
    List<User> displayAllUser();
    Optional<User> viewSingleUser(long user_id);
    void updateUser(User user);

    void deleteUser(long user_id);

}
