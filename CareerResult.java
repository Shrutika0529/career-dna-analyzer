package com.careerdna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_result")
public class CareerResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recommended_career")
    private String recommendedCareer;

    private int score;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "career_name")
    private String careerName;

    @Column(name = "user_id")
    private Long userId;

    public CareerResult() {
    }

    public Long getId() {
        return id;
    }

    public String getRecommendedCareer() {
        return recommendedCareer;
    }

    public void setRecommendedCareer(String recommendedCareer) {
        this.recommendedCareer = recommendedCareer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}