package com.careerdna.controller;

import com.careerdna.dto.RegisterRequest;
import com.careerdna.dto.RegisterResponse;
import com.careerdna.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return registerService.register(request);
    }
}