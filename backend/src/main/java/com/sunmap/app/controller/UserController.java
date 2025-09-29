package com.sunmap.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
import com.sunmap.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        return userService.saveUser(dto);
    }

    // login user
    // will implement later with spring security
    @PostMapping("/login")
    public ResponseEntity<UserViewDTO> loginUser(@RequestBody LoginRequestDTO loginRequest,
            HttpServletResponse response, HttpServletRequest request) {
        // Authenticate the user
        UserViewDTO dto = userService.authenticateUser(loginRequest);
        // Create an authentication token
        Authentication auth = new UsernamePasswordAuthenticationToken(dto.getEmail(), null,
                List.of(new SimpleGrantedAuthority("USER")));
        // Set the security context
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        // Save the security context
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);
        request.getSession(true);

        return ResponseEntity.ok(dto);
    }

    // get user by id
    @GetMapping("/{id}")
    public UserViewDTO getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    // get all users
    @GetMapping
    public List<UserViewDTO> getUsers() {
        return userService.getAllUsers();
    }

    // delete later just for testing
    @GetMapping("/me")
    public ResponseEntity<String> currentUser(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        return ResponseEntity.ok("You are logged in as: " + auth.getName());
    }

    // delete user by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 if successful
    public void deleteById(@PathVariable long id) {
        userService.deleteUser(id);
    }

}
