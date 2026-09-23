package com.careerdna.controller;

import com.careerdna.entity.CareerQuestion;
import com.careerdna.service.CareerQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-questions")
@CrossOrigin
public class CareerQuestionController {

    @Autowired
    private CareerQuestionService careerQuestionService;

    // ADD
    @PostMapping("/add")
    public ResponseEntity<CareerQuestion> addQuestion(
            @RequestBody CareerQuestion question) {

        CareerQuestion savedQuestion =
                careerQuestionService.saveQuestion(question);

        return ResponseEntity.ok(savedQuestion);
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<CareerQuestion>> getAllQuestions() {

        return ResponseEntity.ok(
                careerQuestionService.getAllQuestions()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CareerQuestion> getQuestion(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                careerQuestionService.getQuestionById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CareerQuestion> updateQuestion(
            @PathVariable Long id,
            @RequestBody CareerQuestion question) {

        return ResponseEntity.ok(
                careerQuestionService.updateQuestion(id, question)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long id) {

        careerQuestionService.deleteQuestion(id);

        return ResponseEntity.ok("Career question deleted successfully");
    }
}