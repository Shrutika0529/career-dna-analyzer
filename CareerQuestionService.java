package com.careerdna.service;

import com.careerdna.entity.CareerQuestion;
import com.careerdna.repository.CareerQuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerQuestionService {

    @Autowired
    private CareerQuestionRepository careerQuestionRepository;

    // Save question
    public CareerQuestion saveQuestion(CareerQuestion question) {
        return careerQuestionRepository.save(question);
    }

    // Get all questions
    public List<CareerQuestion> getAllQuestions() {
        return careerQuestionRepository.findAll();
    }

    // Get question by ID
    public CareerQuestion getQuestionById(Long id) {
        return careerQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Career question not found with id: " + id));
    }

    // Delete question
    public void deleteQuestion(Long id) {
        if (!careerQuestionRepository.existsById(id)) {
            throw new RuntimeException("Career question not found with id: " + id);
        }

        careerQuestionRepository.deleteById(id);
    }

    // Update question
    public CareerQuestion updateQuestion(Long id, CareerQuestion updatedQuestion) {

        CareerQuestion existingQuestion = careerQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Career question not found with id: " + id));

        existingQuestion.setQuestion(updatedQuestion.getQuestion());

        existingQuestion.setOption1(updatedQuestion.getOption1());
        existingQuestion.setOption2(updatedQuestion.getOption2());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption4(updatedQuestion.getOption4());

        existingQuestion.setCareer1(updatedQuestion.getCareer1());
        existingQuestion.setCareer2(updatedQuestion.getCareer2());
        existingQuestion.setCareer3(updatedQuestion.getCareer3());
        existingQuestion.setCareer4(updatedQuestion.getCareer4());

        return careerQuestionRepository.save(existingQuestion);
    }
}