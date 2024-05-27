package com.samuelCode.fullWeb.controller;

import com.samuelCode.fullWeb.model.User;
import com.samuelCode.fullWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Object> saveUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User " + user.getId() + " saved successfully");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/displayAllUsers")
    public ResponseEntity<Object> showAllUsers() {
        List<User> listAllUsers = userService.displayAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(listAllUsers);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getUserId")
    public ResponseEntity<Object> saveUser(@PathVariable int user_id) {
        Optional<User> getSingleUser = userService.viewSingleUser( user_id);
        return ResponseEntity.status(HttpStatus.OK).body(getSingleUser);
         }
    }

