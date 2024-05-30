package com.samuelCode.fullWeb.service.impl;

import com.samuelCode.fullWeb.model.User;
import com.samuelCode.fullWeb.repository.UserRepo;
import com.samuelCode.fullWeb.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public void saveUser(User user) {
        try {
            if (userRepo.findByEMail(user.geteMail()) != null) {
                throw new Exception("Username already exists with email: " + user.geteMail());
            }

            userRepo.save(user);

            } catch(Exception e){
                String errorLog = "User email already exist";
                throw new RuntimeException(e + errorLog);
            }

        }
    @Override
    public List<User> displayAllUser() {
       List<User> listAllUsers = userRepo.findAll();
       return listAllUsers;
    }

    @Override
    public Optional<User> viewSingleUser(long user_id) {
        Optional<User> getSingleUser = userRepo.findById(user_id);
        return getSingleUser;

    }


}
