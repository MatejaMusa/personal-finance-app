package com.matejamusa.personal_finance.query;

public class TransactionQuery {
    public static final String CREATE_TRANSACTION_QUERY = "INSERT INTO Transactions (account_id, category_id, amount, transaction_date) VALUES (:accountId, :categoryId, :amount, :transactionDate)";
    public static final String SELECT_TRANSACTIONS_BY_ACCOUNT_ID_PAGEABLE_QUERY = "SELECT * FROM Transactions WHERE account_id = :accountId ORDER BY transaction_date DESC LIMIT :limit OFFSET :offset";
    public static final String COUNT_TRANSACTIONS_BY_ACCOUNT_ID_QUERY = "SELECT COUNT(*) FROM Transactions WHERE account_id = :accountId";
    public static final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM Transactions WHERE id = :id";
}
