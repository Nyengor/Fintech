package org.example.fintech.dto;

public class AddExpenseResponse {

    private String message;

    public AddExpenseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
