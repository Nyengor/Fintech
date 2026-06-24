package org.example.fintech.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.example.fintech.dto.*;
import org.example.fintech.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private Logger logger = LoggerFactory.getLogger(WalletController.class);

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/transactions/incometable")
    public List<IncomeDTO> getIncomeTable() {
        return walletService.getIncomeTable();
    }

    @PostMapping("/transactions/income")
    public AddIncomeResponse addIncome(@RequestBody AddIncomeRequest incomeRequest, HttpServletRequest servletRequest) {
        String ipAddress = servletRequest.getRemoteAddr();
        return walletService.addIncome(incomeRequest, ipAddress);
    }

    @GetMapping("/transactions/expensetable")
    public List<ExpenseDTO> getExpenseTable() {
        return walletService.getExpenseTable();
    }

    @PostMapping("/transactions/expense")
    public AddExpenseResponse addExpense(@RequestBody AddExpenseRequest expenseRequest, HttpServletRequest servletRequest) {
        String ipAddress = servletRequest.getRemoteAddr();
        return walletService.addExpense(expenseRequest, ipAddress);
    }

    @PatchMapping("/transactions/{id}")
    public EditTransactionResponse editIncome(@PathVariable Long id, @RequestBody EditTransactionRequest editTransactionRequest, HttpServletRequest servletRequest) {
        String ipAddress = servletRequest.getRemoteAddr();
        logger.info("This is the balance overview logging test");
        return walletService.editTransaction(id, editTransactionRequest, ipAddress);
    }

    @DeleteMapping("/transactions/{id}")
    public DeleteTransactionResponse deleteIncome(@PathVariable Long id, HttpServletRequest servletRequest) {
        String ipAddress = servletRequest.getRemoteAddr();
        return walletService.deleteTransaction(id, ipAddress);
    }
}
