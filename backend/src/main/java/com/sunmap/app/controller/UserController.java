package com.sunmap.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sunmap.app.DTO.LoginRequestDTO;
import com.sunmap.app.DTO.UserCreateDTO;
import com.sunmap.app.DTO.UserViewDTO;
import com.sunmap.app.service.AuthService;
import com.sunmap.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // api access
    private final UserService userService;
    private final AuthService authService;

    // constructor injection
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // save user
    @PostMapping
    public UserViewDTO createUser(@Valid @RequestBody UserCreateDTO dto, HttpServletResponse response,
            HttpServletRequest request) {
        UserViewDTO newUser = userService.saveUser(dto);
        // Automatically log in the user after registration
        authService.establishSession(newUser.getEmail(), request, response);
        return newUser;
    }

    // login user
    @PostMapping("/login")
    public ResponseEntity<UserViewDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest,
            HttpServletResponse response, HttpServletRequest request) {
        // Authenticate the user
        UserViewDTO dto = userService.authenticateUser(loginRequest);
        // Create an authentication token and establish session
        authService.establishSession(dto.getEmail(), request, response);
        return ResponseEntity.ok(dto);
    }

    // get user by id
    // i want this to check if there is a valid session first
    @GetMapping("/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable long id, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // get all users
    @GetMapping
    public List<UserViewDTO> getUsers() {
        return userService.getAllUsers();
    }

    // delete later just for testing
    @GetMapping("/me")
    public ResponseEntity<UserViewDTO> currentUser(Authentication auth) {
        // this should return the user based on the session
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getUserByEmail(auth.getName()));
    }

    // delete user by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 if successful
    public void deleteById(@PathVariable long id) {
        userService.deleteUser(id);
    }

}
