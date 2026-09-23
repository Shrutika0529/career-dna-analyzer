package com.careerdna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(name = "option1")
    private String optionA;

    @Column(name = "option2")
    private String optionB;

    @Column(name = "option3")
    private String optionC;

    @Column(name = "option4")
    private String optionD;

    @Column(name = "career1")
    private String career1;

    @Column(name = "career2")
    private String career2;

    @Column(name = "career3")
    private String career3;

    @Column(name = "career4")
    private String career4;


    public Question() {
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


    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }


    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }


    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }


    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
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
}