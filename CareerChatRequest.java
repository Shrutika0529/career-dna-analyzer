package com.careerdna.dto;

public class CareerChatRequest {

    private String question;

    private String conversationId;


    public CareerChatRequest() {
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}