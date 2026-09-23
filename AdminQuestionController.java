package com.careerdna.controller;


import com.careerdna.entity.Question;
import com.careerdna.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/questions")
@CrossOrigin("*")
public class AdminQuestionController {


    private final QuestionService service;


    public AdminQuestionController(QuestionService service){
        this.service=service;
    }


    // Admin add question
    @PostMapping("/add")
    public Question addQuestion(
            @RequestBody Question question){

        return service.addQuestion(question);
    }



    // View all questions
    @GetMapping("/all")
    public List<Question> getQuestions(){

        return service.getAllQuestions();
    }

}