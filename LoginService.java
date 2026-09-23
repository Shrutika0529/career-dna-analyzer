package com.careerdna.service;

import com.careerdna.dto.LoginResponse;
import com.careerdna.entity.User;
import com.careerdna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================
    // LOGIN
    // =========================

    public LoginResponse login(String email, String password) {

        Optional<User> optionalUser =
                userRepository.findByEmail(email);

        // =========================
        // USER NOT FOUND
        // =========================

        if (optionalUser.isEmpty()) {

            return new LoginResponse(
                    false,
                    "Invalid Email or Password",
                    null,
                    null,
                    null
            );
        }

        User user = optionalUser.get();

        // =========================
        // WRONG PASSWORD
        // =========================

        if (!user.getPassword().equals(password)) {

            return new LoginResponse(
                    false,
                    "Invalid Email or Password",
                    null,
                    null,
                    null
            );
        }

        // =========================
        // LOGIN SUCCESS
        // =========================

        return new LoginResponse(
                true,
                "Login Successful",
                user.getFullName(),
                user.getRole(),
                user.getId()
        );
    }
}