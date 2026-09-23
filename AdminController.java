package com.careerdna.controller;

import com.careerdna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.careerdna.repository.UserRepository;
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/students")
    public List<User> getAllStudents() {
        return userRepository.findAll();
    }
}