package com.careerdna.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "career_chat_history")
public class CareerChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(name = "student_message", nullable = false, columnDefinition = "TEXT")
    private String studentMessage;

    @Column(name = "ai_response", nullable = false, columnDefinition = "TEXT")
    private String aiResponse;

    @Column(length = 50)
    private String branch;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CareerChatHistory() {
    }

    public Long getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getStudentMessage() {
        return studentMessage;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public String getBranch() {
        return branch;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setStudentMessage(String studentMessage) {
        this.studentMessage = studentMessage;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}