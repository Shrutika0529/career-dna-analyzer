package com.careerdna.service;

import com.careerdna.dto.CareerDNAResponse;
import com.careerdna.entity.CareerDNA;
import com.careerdna.entity.CareerQuestion;
import com.careerdna.entity.StudentAnswer;

import com.careerdna.repository.CareerDNARepository;
import com.careerdna.repository.CareerQuestionRepository;
import com.careerdna.repository.StudentAnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CareerDNAService {

    @Autowired
    private CareerDNARepository repository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private CareerQuestionRepository careerQuestionRepository;


    // =====================================================
    // SAVE CAREER DNA REPORT
    // =====================================================

    public CareerDNA saveReport(CareerDNA report) {
        return repository.save(report);
    }


    // =====================================================
    // GET CAREER DNA REPORT
    // =====================================================

    public Optional<CareerDNA> getReport(Long userId) {
        return repository.findByUserId(userId);
    }


    // =====================================================
    // GENERATE CAREER DNA
    // =====================================================

    public CareerDNAResponse generateCareerDNA(Long studentId) {

        if (studentId == null) {
            throw new IllegalArgumentException(
                    "Student ID cannot be null."
            );
        }


        // =================================================
        // GET STUDENT ANSWERS
        // =================================================

        List<StudentAnswer> answers =
                studentAnswerRepository.findByStudentId(studentId);


        /*
         * IMPORTANT:
         *
         * If there are no answers, the student has not
         * completed the assessment yet.
         */

        if (answers == null || answers.isEmpty()) {

            throw new RuntimeException(
                    "Assessment not completed for student ID: "
                            + studentId
                            + ". Please complete the assessment first."
            );
        }


        // =================================================
        // CAREER SCORE MAP
        // =================================================

        Map<String, Integer> careerScores =
                new HashMap<>();


        // =================================================
        // PROCESS ANSWERS
        // =================================================

        for (StudentAnswer answer : answers) {

            if (answer == null) {
                continue;
            }


            Long questionId =
                    answer.getQuestionId();

            String selectedOption =
                    answer.getSelectedOption();


            // Ignore invalid answer

            if (questionId == null ||
                    selectedOption == null ||
                    selectedOption.trim().isEmpty()) {

                continue;
            }


            selectedOption =
                    selectedOption.trim();


            // =================================================
            // FIND QUESTION
            // =================================================

            Optional<CareerQuestion> optionalQuestion =
                    careerQuestionRepository.findById(questionId);


            if (optionalQuestion.isEmpty()) {

                System.out.println(
                        "Question not found: "
                                + questionId
                );

                continue;
            }


            CareerQuestion question =
                    optionalQuestion.get();


            String career = null;

            Integer score = 0;


            // =================================================
            // OPTION A
            // =================================================

            if ("A".equalsIgnoreCase(selectedOption)
                    || "1".equals(selectedOption)) {

                career = question.getCareer1();

                score = question.getScore1();
            }


            // =================================================
            // OPTION B
            // =================================================

            else if ("B".equalsIgnoreCase(selectedOption)
                    || "2".equals(selectedOption)) {

                career = question.getCareer2();

                score = question.getScore2();
            }


            // =================================================
            // OPTION C
            // =================================================

            else if ("C".equalsIgnoreCase(selectedOption)
                    || "3".equals(selectedOption)) {

                career = question.getCareer3();

                score = question.getScore3();
            }


            // =================================================
            // OPTION D
            // =================================================

            else if ("D".equalsIgnoreCase(selectedOption)
                    || "4".equals(selectedOption)) {

                career = question.getCareer4();

                score = question.getScore4();
            }


            // =================================================
            // INVALID CAREER
            // =================================================

            if (career == null ||
                    career.trim().isEmpty()) {

                continue;
            }


            career = career.trim();


            if (score == null) {
                score = 0;
            }


            // =================================================
            // ADD SCORE
            // =================================================

            careerScores.put(
                    career,
                    careerScores.getOrDefault(career, 0)
                            + score
            );
        }


        // =================================================
        // CHECK CAREER SCORES
        // =================================================

        if (careerScores.isEmpty()) {

            throw new RuntimeException(
                    "No valid career scores found for student ID: "
                            + studentId
                            + ". Check career_questions and student_answers."
            );
        }


        // =================================================
        // FIND RECOMMENDED CAREER
        // =================================================

        String recommendedCareer =
                "Not Available";

        int highestScore = 0;


        for (Map.Entry<String, Integer> entry :
                careerScores.entrySet()) {

            Integer score = entry.getValue();

            if (score == null) {
                continue;
            }


            if (score > highestScore) {

                highestScore = score;

                recommendedCareer =
                        entry.getKey();
            }
        }


        // =================================================
        // CALCULATE MAXIMUM POSSIBLE SCORE
        // =================================================

        int maximumPossibleScore = 0;


        for (StudentAnswer answer : answers) {

            if (answer == null ||
                    answer.getQuestionId() == null) {

                continue;
            }


            Optional<CareerQuestion> optionalQuestion =
                    careerQuestionRepository.findById(
                            answer.getQuestionId()
                    );


            if (optionalQuestion.isEmpty()) {
                continue;
            }


            CareerQuestion question =
                    optionalQuestion.get();


            Integer maxScore =
                    getMaximumScore(question);


            if (maxScore != null &&
                    maxScore > 0) {

                maximumPossibleScore += maxScore;
            }
        }


        // =================================================
        // CALCULATE OVERALL SCORE
        // =================================================

        int overallScore = 0;


        if (maximumPossibleScore > 0) {

            overallScore =
                    (highestScore * 100)
                            / maximumPossibleScore;
        }


        // Keep between 0 and 100

        overallScore =
                Math.max(
                        0,
                        Math.min(
                                100,
                                overallScore
                        )
                );


        // =================================================
        // INDUSTRY READINESS
        // =================================================

        int industryReadiness =
                calculateIndustryReadiness(
                        overallScore
                );


        // =================================================
        // LOG RESULT
        // =================================================

        System.out.println(
                "======================================"
        );

        System.out.println(
                "CAREER DNA GENERATED"
        );

        System.out.println(
                "Student ID: " + studentId
        );

        System.out.println(
                "Recommended Career: "
                        + recommendedCareer
        );

        System.out.println(
                "Overall Score: "
                        + overallScore
        );

        System.out.println(
                "Industry Readiness: "
                        + industryReadiness
        );

        System.out.println(
                "Career Scores: "
                        + careerScores
        );

        System.out.println(
                "======================================"
        );


        // =================================================
        // RETURN RESPONSE
        // =================================================

        return new CareerDNAResponse(
                recommendedCareer,
                overallScore,
                industryReadiness,
                careerScores
        );
    }


    // =====================================================
    // INDUSTRY READINESS
    // =====================================================

    private int calculateIndustryReadiness(
            int overallScore) {

        if (overallScore >= 80) {

            return 90;
        }

        if (overallScore >= 60) {

            return 75;
        }

        if (overallScore >= 40) {

            return 60;
        }

        return 40;
    }


    // =====================================================
    // FIND MAXIMUM SCORE
    // =====================================================

    private Integer getMaximumScore(
            CareerQuestion question) {

        if (question == null) {
            return 0;
        }


        int maximum = 0;


        if (question.getScore1() != null) {

            maximum =
                    Math.max(
                            maximum,
                            question.getScore1()
                    );
        }


        if (question.getScore2() != null) {

            maximum =
                    Math.max(
                            maximum,
                            question.getScore2()
                    );
        }


        if (question.getScore3() != null) {

            maximum =
                    Math.max(
                            maximum,
                            question.getScore3()
                    );
        }


        if (question.getScore4() != null) {

            maximum =
                    Math.max(
                            maximum,
                            question.getScore4()
                    );
        }


        return maximum;
    }
}