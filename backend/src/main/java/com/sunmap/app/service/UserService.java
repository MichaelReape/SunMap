package com.sunmap.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sunmap.app.entity.User;
import com.sunmap.app.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    // inject repository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // save the user
    public User saveUser(User user) {
        return userRepository.save(user);
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

}
