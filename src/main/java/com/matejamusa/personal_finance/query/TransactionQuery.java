package com.matejamusa.personal_finance.query;

public class TransactionQuery {
    public static final String CREATE_TRANSACTION_QUERY = "INSERT INTO Transactions (account_id, category_id, amount, transaction_date) VALUES (:accountId, :categoryId, :amount, :transactionDate)";
    public static final String SELECT_TRANSACTIONS_BY_ACCOUNT_ID_PAGEABLE_QUERY = "SELECT t.id, t.amount, t.transaction_date, c.name AS category_name, c.type FROM Transactions t JOIN Categories c ON t.category_id = c.id WHERE t.account_id = :accountId ORDER BY t.transaction_date DESC LIMIT :limit OFFSET :offset";
    public static final String COUNT_TRANSACTIONS_BY_ACCOUNT_ID_QUERY = "SELECT COUNT(*) FROM Transactions WHERE account_id = :accountId";
    public static final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM Transactions WHERE id = :id";
    public static final String SELECT_GRAPH_BY_ACCOUNT_ID_AND_TYPE = "SELECT c.name AS name, SUM(t.amount) / (SELECT SUM(t2.amount) FROM Transactions t2 JOIN Categories c2 ON c2.id = t2.category_id WHERE t2.account_id = :accountId AND c2.type = :type) * 100 AS percentage FROM Categories c JOIN Transactions t ON c.id = t.category_id WHERE t.account_id = :accountId AND c.type = :type GROUP BY c.id, c.name";
}
