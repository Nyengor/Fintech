package org.example.fintech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BalanceOverviewDto {

    private LocalDate date;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;

    public BalanceOverviewDto() {
    }

//    private BigDecimal running;


    public BalanceOverviewDto(LocalDate date, BigDecimal income, BigDecimal expense, BigDecimal balance) {
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public BalanceOverviewDto(LocalDate date, BigDecimal income, BigDecimal expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }
}