package org.example.fintech.dto;

public class DeleteTransactionResponse {

    private String message;

    public DeleteTransactionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
