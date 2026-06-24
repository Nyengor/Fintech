package org.example.fintech.dto;

import org.example.fintech.entity.Category;
import org.example.fintech.entity.TransactionType;

import java.math.BigDecimal;

public class AddIncomeRequest {
    private BigDecimal amount;

    private String description;

    private Category category;

    private TransactionType type;

    public AddIncomeRequest(BigDecimal amount, String description, Category category, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
