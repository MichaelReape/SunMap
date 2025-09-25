package com.sunmap.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunmap.app.DTO.UserCreateDTO;
import com.sunmap.app.DTO.UserViewDTO;
import com.sunmap.app.entity.User;
import com.sunmap.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // api access
    private final UserService userService;

    // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // save user
    @PostMapping
    public UserViewDTO createUser(@RequestBody UserCreateDTO dto) {
        System.out.println("In UserController.java");
        System.out.println("Creating user with email: " + dto.getEmail());
        return userService.saveUser(dto);
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
