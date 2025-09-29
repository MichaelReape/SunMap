package com.sunmap.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sunmap.app.DTO.LoginRequestDTO;
import com.sunmap.app.DTO.UserCreateDTO;
import com.sunmap.app.DTO.UserViewDTO;
import com.sunmap.app.entity.User;
import com.sunmap.app.repository.UserRepository;

@Service
public class UserService {
    // inject repository and password encoder
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // constructor injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // save the user
    public UserViewDTO saveUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User newUser = new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(newUser);
        return convertToViewDTO(savedUser);
    }

    // get user by id
    public UserViewDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return convertToViewDTO(user);
    }

    // get all users
    public List<UserViewDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToViewDTO).toList();
    }

    // delete user by id
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    // Authenticate user
    public UserViewDTO authenticateUser(LoginRequestDTO requestDTO) {

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return convertToViewDTO(user);
    }

    // convert to view DTO
    private UserViewDTO convertToViewDTO(User user) {
        return new UserViewDTO(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
