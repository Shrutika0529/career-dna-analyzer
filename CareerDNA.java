package com.careerdna.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "career_dna")
public class CareerDNA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "programming_score")
    private Integer programmingScore;

    @Column(name = "problem_solving_score")
    private Integer problemSolvingScore;

    @Column(name = "database_score")
    private Integer databaseScore;

    @Column(name = "communication_score")
    private Integer communicationScore;

    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(name = "recommended_career")
    private String recommendedCareer;

    @Column(name = "industry_readiness")
    private Integer industryReadiness;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CareerDNA() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Integer programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Integer getProblemSolvingScore() {
        return problemSolvingScore;
    }

    public void setProblemSolvingScore(Integer problemSolvingScore) {
        this.problemSolvingScore = problemSolvingScore;
    }

    public Integer getDatabaseScore() {
        return databaseScore;
    }

    public void setDatabaseScore(Integer databaseScore) {
        this.databaseScore = databaseScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(Integer communicationScore) {
        this.communicationScore = communicationScore;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public String getRecommendedCareer() {
        return recommendedCareer;
    }

    public void setRecommendedCareer(String recommendedCareer) {
        this.recommendedCareer = recommendedCareer;
    }

    public Integer getIndustryReadiness() {
        return industryReadiness;
    }

    public void setIndustryReadiness(Integer industryReadiness) {
        this.industryReadiness = industryReadiness;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}