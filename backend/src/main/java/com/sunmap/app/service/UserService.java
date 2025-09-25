package com.sunmap.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sunmap.app.DTO.UserCreateDTO;
import com.sunmap.app.DTO.UserViewDTO;
import com.sunmap.app.entity.User;
import com.sunmap.app.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // inject repository and password encoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // save the user
    public UserViewDTO saveUser(UserCreateDTO dto) {
        System.out.println("In UserService.java");
        User newUser = new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(newUser);
        return convertToViewDTO(savedUser);
    }

    // get user by id
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // convert to view DTO
    private UserViewDTO convertToViewDTO(User user) {
        return new UserViewDTO(user.getId(), user.getEmail(), user.getCreatedAt());
    }

}
