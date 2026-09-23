package com.careerdna.controller;

import com.careerdna.dto.CareerChatRequest;
import com.careerdna.dto.CareerChatResponse;
import com.careerdna.service.CareerChatService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/career-chat")
@CrossOrigin
public class CareerChatController {

    private final CareerChatService careerChatService;

    public CareerChatController(
            CareerChatService careerChatService) {

        this.careerChatService = careerChatService;
    }

    @PostMapping("/ask")
    public CareerChatResponse askCareerAI(
            @RequestBody CareerChatRequest request) {

        return careerChatService.askQuestion(
                request.getQuestion(),
                request.getConversationId()
        );
    }
}