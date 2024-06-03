package com.matejamusa.personal_finance.query;

public class AccountQuery {
    public static final String CREATE_ACCOUNT_QUERY = "INSERT INTO Accounts (name, user_id, description, priority, balance) VALUES (:name, :userId, :description, :priority, 0)";
    public static final String SELECT_ACCOUNT_BY_ID_QUERY = "SELECT * FROM Accounts WHERE id = :id";
    public static final String SELECT_ACCOUNTS_BY_USER_ID_QUERY = "SELECT * FROM Accounts WHERE user_id = :userId";
}
