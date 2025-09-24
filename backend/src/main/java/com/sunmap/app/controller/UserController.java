package com.sunmap.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunmap.app.entity.User;
import com.sunmap.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // api access
    private UserService userService;

    // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // save user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    // get all users
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

}
