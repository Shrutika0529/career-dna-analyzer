package com.careerdna.service;

import com.careerdna.entity.CareerQuestion;
import com.careerdna.entity.CareerResult;
import com.careerdna.entity.StudentAnswer;
import com.careerdna.entity.User;

import com.careerdna.repository.CareerQuestionRepository;
import com.careerdna.repository.CareerResultRepository;
import com.careerdna.repository.StudentAnswerRepository;
import com.careerdna.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecommendationService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private CareerQuestionRepository careerQuestionRepository;

    @Autowired
    private CareerResultRepository careerResultRepository;

    @Autowired
    private UserRepository userRepository;


    public CareerResult generateRecommendation(Long studentId) {

        // ==========================================
        // GET STUDENT ANSWERS
        // ==========================================

        List<StudentAnswer> answers =
                studentAnswerRepository.findByStudentId(studentId);

        if (answers == null || answers.isEmpty()) {

            throw new RuntimeException(
                    "No assessment answers found for student."
            );
        }


        // ==========================================
        // CAREER SCORE
        // ==========================================

        Map<String, Integer> careerScores =
                new HashMap<>();


        // ==========================================
        // PROCESS EACH ANSWER
        // ==========================================

        for (StudentAnswer answer : answers) {

            Long questionId =
                    answer.getQuestionId();

            String selectedOption =
                    answer.getSelectedOption();


            Optional<CareerQuestion> optionalQuestion =
                    careerQuestionRepository.findById(questionId);


            if (optionalQuestion.isEmpty()) {
                continue;
            }


            CareerQuestion question =
                    optionalQuestion.get();


            String career = null;


            // ==========================================
            // OPTION A
            // ==========================================

            if ("A".equalsIgnoreCase(selectedOption)
                    || "1".equalsIgnoreCase(selectedOption)) {

                career = question.getCareer1();
            }


            // ==========================================
            // OPTION B
            // ==========================================

            else if ("B".equalsIgnoreCase(selectedOption)
                    || "2".equalsIgnoreCase(selectedOption)) {

                career = question.getCareer2();
            }


            // ==========================================
            // OPTION C
            // ==========================================

            else if ("C".equalsIgnoreCase(selectedOption)
                    || "3".equalsIgnoreCase(selectedOption)) {

                career = question.getCareer3();
            }


            // ==========================================
            // OPTION D
            // ==========================================

            else if ("D".equalsIgnoreCase(selectedOption)
                    || "4".equalsIgnoreCase(selectedOption)) {

                career = question.getCareer4();
            }


            // ==========================================
            // ADD SCORE
            // ==========================================

            if (career != null && !career.trim().isEmpty()) {

                career = career.trim();

                careerScores.put(
                        career,
                        careerScores.getOrDefault(career, 0) + 1
                );
            }
        }


        // ==========================================
        // NO CAREER FOUND
        // ==========================================

        if (careerScores.isEmpty()) {

            throw new RuntimeException(
                    "No career mapping found. Please add career1, career2, career3 and career4 values in career_questions table."
            );
        }


        // ==========================================
        // FIND HIGHEST CAREER
        // ==========================================

        String recommendedCareer =
                "Not Available";

        int highestScore = 0;


        for (Map.Entry<String, Integer> entry :
                careerScores.entrySet()) {

            if (entry.getValue() > highestScore) {

                highestScore = entry.getValue();

                recommendedCareer = entry.getKey();
            }
        }


        // ==========================================
        // GET USER
        // ==========================================

        Optional<User> optionalUser =
                userRepository.findById(studentId);

        if (optionalUser.isEmpty()) {

            throw new RuntimeException(
                    "Student not found."
            );
        }


        User user = optionalUser.get();


        // ==========================================
        // GET EXISTING RESULT
        // ==========================================

        Optional<CareerResult> existing =
                careerResultRepository.findByUserId(studentId);


        CareerResult result;

        if (existing.isPresent()) {

            result = existing.get();

        } else {

            result = new CareerResult();
        }


        // ==========================================
        // SAVE RESULT
        // ==========================================

        result.setUserId(studentId);

        result.setStudentEmail(user.getEmail());

        result.setCareerName(recommendedCareer);

        result.setRecommendedCareer(recommendedCareer);

        result.setScore(highestScore);


        return careerResultRepository.save(result);
    }
}