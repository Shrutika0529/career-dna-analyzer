package com.careerdna.controller;

import com.careerdna.entity.StudentAnswer;
import com.careerdna.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin("*")
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService service;

    @PostMapping("/submit")
    public StudentAnswer submitAnswer(@RequestBody StudentAnswer answer) {
        return service.saveAnswer(answer);
    }

    @GetMapping("/{studentId}")
    public List<StudentAnswer> getStudentAnswers(@PathVariable Long studentId) {
        return service.getStudentAnswers(studentId);
    }
}