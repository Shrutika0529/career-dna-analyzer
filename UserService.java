package com.careerdna.service;

import com.careerdna.dto.LoginRequest;
import com.careerdna.dto.RegisterRequest;
import com.careerdna.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.careerdna.repository.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ================= REGISTER =================

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // ================= LOGIN =================

    public User login(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (user.getPassword().equals(request.getPassword())) {
                return user;
            }

        }

        return null;
    }

}