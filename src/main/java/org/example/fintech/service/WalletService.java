package org.example.fintech.service;

import org.example.fintech.dto.*;
import org.example.fintech.entity.Category;
import org.example.fintech.entity.TransactionType;
import org.example.fintech.entity.Transactions;
import org.example.fintech.event.AuditEvent;
import org.example.fintech.repository.CategoryRepository;
import org.example.fintech.repository.TransactionTypeRepository;
import org.example.fintech.repository.TransactionsRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {

    private TransactionsRepository transactionsRepository;
    private TransactionTypeRepository transactionTypeRepository;
    private CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;


    public WalletService(TransactionsRepository transactionsRepository, TransactionTypeRepository transactionTypeRepository, CategoryRepository categoryRepository, ApplicationEventPublisher eventPublisher) {
        this.transactionsRepository = transactionsRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    //    To get useremail straight from jwt
    private String getCurrentUserEmail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }


    private TransactionType getTransactionType(String typeName) {

        return transactionTypeRepository
                .findByTransactionName(typeName)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Transaction type not found"
                        )
                );
    }

    public List<IncomeDTO> getIncomeTable() {
        return transactionsRepository.getAllIncome();
    }

    @Transactional
    public AddIncomeResponse addIncome(AddIncomeRequest incomeRequest, String ipAddress) {
        try{
            Transactions transactions = new Transactions();
            transactions.setAmount(incomeRequest.getAmount());
            transactions.setDescription(incomeRequest.getDescription());

            Category category = categoryRepository.findByCategoryName(incomeRequest.getCategory().getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + incomeRequest.getCategory().getCategoryName()));

            transactions.setCategory(category);
            if (incomeRequest.getCategory() == null || incomeRequest.getCategory().getCategoryName() == null) {
                throw new IllegalArgumentException("Transaction category name must be provided.");
            }
            transactions.setTypeId(getTransactionType("Income"));

            transactionsRepository.save(transactions);

            eventPublisher.publishEvent(new AuditEvent(
                    "SUCCESS",
                    ipAddress,
                    "Added income entry: " + incomeRequest.getDescription() + " (Amount: " + incomeRequest.getAmount() + ")",
                    "TRANSACTIONS",
                    transactions.getId().toString(),
                    getCurrentUserEmail(),
                    LocalDateTime.now()
            ));
            return new AddIncomeResponse("Income has been added");
        }  catch (Exception e) {
            // FAILED AUDIT
            eventPublisher.publishEvent(new AuditEvent(
                    "FAILED",
                    ipAddress,
                    "Failed to add income: " + e.getMessage(),
                    "TRANSACTIONS",
                    "N/A",
                    getCurrentUserEmail(),
                    LocalDateTime.now()
            ));
            throw e;
        }
    }

    public List<ExpenseDTO> getExpenseTable() {
        return transactionsRepository.getAllExpense();
    }
@Transactional
    public AddExpenseResponse addExpense(AddExpenseRequest expenseRequest, String ipAddress) {
        try {
            Transactions transactions = new Transactions();
            transactions.setAmount(expenseRequest.getAmount());
            transactions.setDescription(expenseRequest.getDescription());
            Category category = categoryRepository.findByCategoryName(expenseRequest.getCategory().getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + expenseRequest.getCategory().getCategoryName()));
            if (expenseRequest.getCategory() == null || expenseRequest.getCategory().getCategoryName() == null) {
                throw new IllegalArgumentException("Transaction category name must be provided.");
            }
            transactions.setCategory(category);
            transactions.setTypeId(getTransactionType("Expense"));

            transactionsRepository.save(transactions);

            eventPublisher.publishEvent(new AuditEvent(
                    "SUCCESS",
                    ipAddress,
                    "Added expense entry: " + expenseRequest.getDescription() + " (Amount: " + expenseRequest.getAmount() + ")",
                    "TRANSACTIONS",
                    transactions.getId().toString(),
                    getCurrentUserEmail(),
                    LocalDateTime.now()
            ));
            return new AddExpenseResponse("Expense has been added");
        } catch (Exception e) {
            // FAILED AUDIT
            eventPublisher.publishEvent(new AuditEvent(
                    "FAILED",
                    ipAddress,
                    "Failed to add expense: " + e.getMessage(),
                    "TRANSACTIONS",
                    "N/A",
                    getCurrentUserEmail(),
                    LocalDateTime.now()
            ));
            throw e;
        }
    }

    public EditTransactionResponse editTransaction(Long id, EditTransactionRequest editTransactionRequest, String ipAddress) {
        Transactions transaction =
                transactionsRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Transaction not found"
                                )
                        );
        transaction.setAmount(editTransactionRequest.getAmount());
        transaction.setDescription(editTransactionRequest.getDescription());
        Category category = categoryRepository.findByCategoryName(editTransactionRequest.getCategory().getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found: " + editTransactionRequest.getCategory().getCategoryName()));
        transaction.setCategory(category);


        transactionsRepository.save(transaction);

        eventPublisher.publishEvent(
                new AuditEvent(
                        "SUCCESS",
                        ipAddress,
                        "UPDATED_TRANSACTION",
                        "TRANSACTIONS",
                        id.toString(),
                        getCurrentUserEmail(),
                        LocalDateTime.now()
                )
        );
        return new EditTransactionResponse(transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription());
    }

    public DeleteTransactionResponse deleteTransaction(Long id, String ipAddress) {
            try{
                Transactions transaction =
                        transactionsRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Transaction not found"));

                transactionsRepository.delete(transaction);

                eventPublisher.publishEvent(new AuditEvent(
                        "SUCCESS",
                        ipAddress,
                        "Deleted transaction with ID: " + id,
                        "TRANSACTIONS",
                        id.toString(),
                        getCurrentUserEmail(),
                        LocalDateTime.now()
                ));

                return new DeleteTransactionResponse("The transaction has been successfully deleted");

            } catch (Exception e) {
                // FAILED AUDIT
                eventPublisher.publishEvent(new AuditEvent(
                        "FAILED",
                        ipAddress,
                        "Failed to delete transaction with ID: " + id + ". Error: " + e.getMessage(),
                        "TRANSACTIONS",
                        id.toString(),
                        getCurrentUserEmail(),
                        LocalDateTime.now()
                ));
                throw e;
            }
        }
}
