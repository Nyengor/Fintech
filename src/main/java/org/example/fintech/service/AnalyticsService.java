package org.example.fintech.service;

import org.example.fintech.dto.BalanceOverviewDto;
import org.example.fintech.dto.BudgetVsExpenseDto;
import org.example.fintech.dto.DashboardSummaryDto;
import org.example.fintech.dto.CategoryStatisticsDto;
import org.example.fintech.repository.BudgetRepository;
import org.example.fintech.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private TransactionsRepository transactionsRepository;
    private BudgetRepository budgetRepository;

    public AnalyticsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.budgetRepository = budgetRepository;
    }

//    Dashboard Summary Logic
    public DashboardSummaryDto getDashboardSummary() {

//      Create variable for each category and connect to repo
        BigDecimal income = transactionsRepository.getTotalIncome();
        BigDecimal expenses = transactionsRepository.getTotalExpense();
//      Check if the field is empty
        if (income == null) {
            income = BigDecimal.ZERO;
        }

        if (expenses == null) {
            expenses = BigDecimal.ZERO;
        }
//      Calculate for balance
        BigDecimal balance = income.subtract(expenses);

//      Create object and set the answers
        DashboardSummaryDto dto = new DashboardSummaryDto();
        dto.setTotalIncome(income);
        dto.setTotalExpenses(expenses);
        dto.setBalance(balance);

        return dto;
    }

//    Balance Overview Logic
    public List<BalanceOverviewDto> getBalanceOverview() {
        List<BalanceOverviewDto> rows = transactionsRepository.getBalanceOverview();
//        final BigDecimal running = BigDecimal.ZERO;

        return  rows.stream().map(row -> {
            LocalDate date =  row.getDate();
            BigDecimal income =row.getIncome();
            BigDecimal expense =row.getExpense();
            BigDecimal balance = income.subtract(expense); // service owns this
            // and this

            return new BalanceOverviewDto(date, income, expense, balance);
        }).toList();

    }

    public List<CategoryStatisticsDto> getCategoryStatistics() {

        List<CategoryStatisticsDto> rows = transactionsRepository.getCategoryStatistics();

        return rows.stream()
                .map(row -> new CategoryStatisticsDto(
                        row.getCategoryName(),
                        row.getTotalAmount(),
                        row.getPercentage()
                ))
                .collect(Collectors.toList());
    }

    public BudgetVsExpenseDto getBudgetAndExpense() {

        return null;


    }
}
