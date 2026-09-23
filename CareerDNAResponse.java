package com.careerdna.dto;

import java.util.Map;

public class CareerDNAResponse {

    private String recommendedCareer;
    private Integer overallScore;
    private Integer industryReadiness;
    private Map<String, Integer> careerScores;

    public CareerDNAResponse() {
    }

    public CareerDNAResponse(
            String recommendedCareer,
            Integer overallScore,
            Integer industryReadiness,
            Map<String, Integer> careerScores) {

        this.recommendedCareer = recommendedCareer;
        this.overallScore = overallScore;
        this.industryReadiness = industryReadiness;
        this.careerScores = careerScores;
    }

    public String getRecommendedCareer() {
        return recommendedCareer;
    }

    public void setRecommendedCareer(String recommendedCareer) {
        this.recommendedCareer = recommendedCareer;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public Integer getIndustryReadiness() {
        return industryReadiness;
    }

    public void setIndustryReadiness(Integer industryReadiness) {
        this.industryReadiness = industryReadiness;
    }

    public Map<String, Integer> getCareerScores() {
        return careerScores;
    }

    public void setCareerScores(Map<String, Integer> careerScores) {
        this.careerScores = careerScores;
    }
}