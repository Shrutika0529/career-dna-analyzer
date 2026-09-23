package com.careerdna.controller;

import com.careerdna.entity.CareerResult;
import com.careerdna.repository.CareerResultRepository;
import com.careerdna.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/recommendation")
@CrossOrigin("*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CareerResultRepository careerResultRepository;


    // ==========================================
    // GENERATE RECOMMENDATION
    // ==========================================

    @PostMapping("/{studentId}")
    public CareerResult generateRecommendation(
            @PathVariable Long studentId) {

        System.out.println(
                "POST Recommendation called for studentId = "
                        + studentId
        );

        CareerResult result =
                recommendationService.generateRecommendation(studentId);

        System.out.println(
                "Generated Result = " + result
        );

        return result;
    }


    // ==========================================
    // GET CAREER RESULT
    // ==========================================

    @GetMapping("/{studentId}")
    public CareerResult getResult(
            @PathVariable Long studentId) {

        System.out.println(
                "GET Recommendation called for studentId = "
                        + studentId
        );

        Optional<CareerResult> result =
                careerResultRepository.findByUserId(studentId);

        System.out.println(
                "Database Result = " + result
        );

        return result.orElse(null);
    }
}