package com.careerdna.service;

import com.careerdna.entity.Question;
import com.careerdna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    // Admin adds question
    public Question addQuestion(Question question) {
        return repository.save(question);
    }

    // Student gets all questions
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }
}