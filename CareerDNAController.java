package com.careerdna.controller;

import com.careerdna.dto.CareerDNAResponse;
import com.careerdna.service.CareerDNAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/career-dna")
@CrossOrigin("*")
public class CareerDNAController {

    @Autowired
    private CareerDNAService careerDNAService;

    @GetMapping("/test")
    public String test() {
        return "Career DNA Controller Working";
    }

    @GetMapping("/{studentId}")
    public CareerDNAResponse getCareerDNA(@PathVariable Long studentId) {
        return careerDNAService.generateCareerDNA(studentId);
    }
}