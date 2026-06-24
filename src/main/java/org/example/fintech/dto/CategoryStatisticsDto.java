package org.example.fintech.dto;

import java.math.BigDecimal;

public class CategoryStatisticsDto {
    private String categoryName;
    private BigDecimal totalAmount;
    private Double percentage;

    public CategoryStatisticsDto(String categoryName, BigDecimal totalAmount, Double percentage) {
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
        this.percentage = percentage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
