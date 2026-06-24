package org.example.fintech.controller;

import org.example.fintech.dto.BalanceOverviewDto;
import org.example.fintech.dto.BudgetVsExpenseDto;
import org.example.fintech.dto.DashboardSummaryDto;
import org.example.fintech.dto.CategoryStatisticsDto;
import org.example.fintech.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
    private AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public DashboardSummaryDto getDashboardSummary() {
        return analyticsService.getDashboardSummary();
    }

    @GetMapping("/categories/stats")
    public List<CategoryStatisticsDto> getStatistics() {
        return analyticsService.getCategoryStatistics();
    }

    @GetMapping("/balance/overview")
    public List<BalanceOverviewDto> getBalanceOverview() {
        logger.info("This is the balance overview logging test");
        return analyticsService.getBalanceOverview();
    }

    @GetMapping("/budget/expense")
    public BudgetVsExpenseDto getBudgetAndExpense() {
        return analyticsService.getBudgetAndExpense();
    }
}
