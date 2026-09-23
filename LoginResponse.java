package com.careerdna.dto;

public class LoginResponse {

    private boolean success;
    private String message;
    private String fullName;
    private String role;
    private Long userId;

    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public LoginResponse() {
    }

    // =========================
    // 5-PARAMETER CONSTRUCTOR
    // =========================

    public LoginResponse(
            boolean success,
            String message,
            String fullName,
            String role,
            Long userId) {

        this.success = success;
        this.message = message;
        this.fullName = fullName;
        this.role = role;
        this.userId = userId;
    }

    // =========================
    // GETTERS
    // =========================

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }

    // =========================
    // SETTERS
    // =========================

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}