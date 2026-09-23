package com.careerdna.service;

import com.careerdna.entity.CareerResult;
import com.careerdna.repository.CareerResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIRecommendationService {

    @Autowired
    private CareerResultRepository repository;

    // Generate career result
    public CareerResult generateResult(Long userId) {

        CareerResult result = new CareerResult();

        result.setUserId(userId);

        // Temporary recommendation logic
        result.setCareerName("AI Engineer");

        result.setScore(92);

        return repository.save(result);
    }

    // Get career result
    public CareerResult getResult(Long userId) {

        return repository
                .findByUserId(userId)
                .orElse(null);
    }
}