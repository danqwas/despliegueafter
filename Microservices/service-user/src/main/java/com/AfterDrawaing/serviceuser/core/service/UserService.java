package com.AfterDrawaing.serviceuser.core.service;

import java.util.List;
import java.util.Optional;

import com.AfterDrawaing.serviceuser.core.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);



    User updateUser(Long userId, User userRequest);

    User saveUser(User user);

    ResponseEntity<?> deleteUser(Long userId);
}
