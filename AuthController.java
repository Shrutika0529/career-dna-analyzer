package com.careerdna.controller;

import com.careerdna.dto.LoginRequest;
import com.careerdna.dto.LoginResponse;
import com.careerdna.dto.RegisterRequest;
import com.careerdna.dto.RegisterResponse;
import com.careerdna.service.LoginService;
import com.careerdna.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return loginService.login(
                request.getEmail(),
                request.getPassword()
        );
    }


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody RegisterRequest request) {

        return registerService.register(request);
    }
}