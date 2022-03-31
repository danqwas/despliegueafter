package com.afterdrawing.backendapi.core.service;

import com.afterdrawing.backendapi.core.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);



    User updateUser(Long userId, User userRequest);

    User saveUser(User user);

    ResponseEntity<?> deleteUser(Long userId);
}