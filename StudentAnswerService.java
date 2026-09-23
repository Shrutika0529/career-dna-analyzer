package com.careerdna.service;

import com.careerdna.entity.CareerQuestion;
import com.careerdna.entity.StudentAnswer;
import com.careerdna.repository.CareerQuestionRepository;
import com.careerdna.repository.StudentAnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository repository;

    @Autowired
    private CareerQuestionRepository careerQuestionRepository;

    // =====================================================
    // SAVE STUDENT ANSWER
    // =====================================================

    public StudentAnswer saveAnswer(StudentAnswer answer) {

        if (answer == null) {
            throw new RuntimeException("Answer cannot be null.");
        }

        if (answer.getStudentId() == null) {
            throw new RuntimeException("Student ID is required.");
        }

        if (answer.getQuestionId() == null) {
            throw new RuntimeException("Question ID is required.");
        }

        if (answer.getSelectedOption() == null ||
                answer.getSelectedOption().trim().isEmpty()) {

            throw new RuntimeException("Selected option is required.");
        }

        // -------------------------------------------------
        // Check question exists
        // -------------------------------------------------

        CareerQuestion question =
                careerQuestionRepository
                        .findById(answer.getQuestionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Career question not found with ID: "
                                                + answer.getQuestionId()
                                )
                        );

        // -------------------------------------------------
        // Normalize selected option
        // -------------------------------------------------

        String option =
                answer.getSelectedOption()
                        .trim()
                        .toUpperCase();

        switch (option) {

            case "A":
            case "1":
            case "OPTION1":
                option = "A";
                break;

            case "B":
            case "2":
            case "OPTION2":
                option = "B";
                break;

            case "C":
            case "3":
            case "OPTION3":
                option = "C";
                break;

            case "D":
            case "4":
            case "OPTION4":
                option = "D";
                break;

            default:
                throw new RuntimeException(
                        "Invalid option. Please select A, B, C or D."
                );
        }

        answer.setSelectedOption(option);

        // -------------------------------------------------
        // Save to database
        // -------------------------------------------------

        return repository.save(answer);
    }

    // =====================================================
    // GET ALL ANSWERS OF A STUDENT
    // =====================================================

    public List<StudentAnswer> getStudentAnswers(Long studentId) {

        if (studentId == null) {
            throw new RuntimeException("Student ID is required.");
        }

        return repository.findByStudentId(studentId);
    }
}