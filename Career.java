package com.careerdna.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String careerName;

    @Column(length = 1000)
    private String description;

    private String skills;

    private String courses;

    private double salary;

    public Career() {
    }

    public Career(Long id, String careerName, String description,
                  String skills, String courses, double salary) {
        this.id = id;
        this.careerName = careerName;
        this.description = description;
        this.skills = skills;
        this.courses = courses;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}