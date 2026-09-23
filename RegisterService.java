package com.careerdna.service;

import com.careerdna.dto.RegisterRequest;
import com.careerdna.dto.RegisterResponse;
import com.careerdna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.careerdna.repository.UserRepository;
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            return new RegisterResponse(false, "Email already exists");

        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userRepository.save(user);

        return new RegisterResponse(true, "Registration Successful");
    }
}