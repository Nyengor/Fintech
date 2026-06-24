package org.example.fintech.dto;

import java.math.BigDecimal;

public class BudgetVsExpenseDto {

    private BigDecimal budgetAmount;
    private BigDecimal expenseAmount;
    private String month;

    public BudgetVsExpenseDto(BigDecimal budgetAmount, String month, BigDecimal expenseAmount) {
        this.budgetAmount = budgetAmount;
        this.month = month;
        this.expenseAmount = expenseAmount;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
