package org.example.fintech.repository;

import org.example.fintech.dto.BalanceOverviewDto;
import org.example.fintech.dto.CategoryStatisticsDto;
import org.example.fintech.dto.ExpenseDTO;
import org.example.fintech.dto.IncomeDTO;
import org.example.fintech.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    Optional<Transactions> findById(
            Long id,
            Long typeId
    );


    // Adds up all INCOME transactions
    // COALESCE means "if SUM returns null, return 0 instead"
    @Query("SELECT COALESCE(SUM(t.amount)) " + " " +
            "FROM Transactions t " + " " +
            "WHERE t.typeId.transactionName ='Income'")
    BigDecimal getTotalIncome();

    // Adds up all EXPENSE transactions
    @Query("SELECT COALESCE(SUM(t.amount))" + " " +
            "FROM Transactions t" + " " +
            "WHERE t.typeId.transactionName ='Expense'")
    BigDecimal getTotalExpense();

//    Selecting and doing the calculations for category pie chart
@Query("SELECT new org.example.fintech.dto.CategoryStatisticsDto(" +
        "t.category.categoryName, " +
        "SUM(t.amount), " +
        "SUM(t.amount) * 100.0 / SUM(SUM(t.amount)) OVER () )" +
        "FROM Transactions t " +
        "WHERE t.typeId.transactionName = 'Expense' " +
        "GROUP BY t.category.categoryName " +
        "ORDER BY SUM(t.amount) DESC")
List<CategoryStatisticsDto> getCategoryStatistics();

// Balance Overview query
@Query("""
    SELECT new org.example.fintech.dto.BalanceOverviewDto(
        t.transactionDate,
        COALESCE(SUM(CASE WHEN t.typeId.id = 1 THEN t.amount END), 0),
        COALESCE(SUM(CASE WHEN t.typeId.id = 2 THEN t.amount END), 0)
    )
    FROM Transactions t
    GROUP BY t.transactionDate
    ORDER BY t.transactionDate ASC
""")
List<BalanceOverviewDto> getBalanceOverview();

//Wallet Page

//    Income Table Query
@Query("SELECT new org.example.fintech.dto.IncomeDTO(t.id, t.amount, t.description) " +
        "FROM Transactions t " +
        "WHERE t.typeId.transactionName = 'Income'")
List<IncomeDTO> getAllIncome();


    //    Expense Table Query
    @Query("SELECT new org.example.fintech.dto.ExpenseDTO(t.id, t.amount, t.description) " +
            "FROM Transactions t " +
            "WHERE t.typeId.transactionName = 'Expense'")
    List<ExpenseDTO> getAllExpense();
}


