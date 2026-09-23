package com.careerdna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_questions")
public class CareerQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @Column(name = "option4")
    private String option4;

    @Column(name = "career1")
    private String career1;

    @Column(name = "career2")
    private String career2;

    @Column(name = "career3")
    private String career3;

    @Column(name = "career4")
    private String career4;

    @Column(name = "score1")
    private Integer score1 = 5;

    @Column(name = "score2")
    private Integer score2 = 5;

    @Column(name = "score3")
    private Integer score3 = 5;

    @Column(name = "score4")
    private Integer score4 = 5;

    public CareerQuestion() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }


    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }


    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }


    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }


    public String getCareer1() {
        return career1;
    }

    public void setCareer1(String career1) {
        this.career1 = career1;
    }


    public String getCareer2() {
        return career2;
    }

    public void setCareer2(String career2) {
        this.career2 = career2;
    }


    public String getCareer3() {
        return career3;
    }

    public void setCareer3(String career3) {
        this.career3 = career3;
    }


    public String getCareer4() {
        return career4;
    }

    public void setCareer4(String career4) {
        this.career4 = career4;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getScore3() {
        return score3;
    }

    public void setScore3(Integer score3) {
        this.score3 = score3;
    }

    public Integer getScore4() {
        return score4;
    }

    public void setScore4(Integer score4) {
        this.score4 = score4;
    }
}