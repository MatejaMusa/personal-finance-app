package com.matejamusa.personal_finance.query;

public class UserQuery {
    public static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM Users WHERE id = :id";
    public static final String SELECT_USER_BY_USERNAME_QUERY = "SELECT * FROM Users WHERE username = :username";
    public static final String COUNT_USER_BY_USERNAME_QUERY = "SELECT COUNT(*) FROM Users WHERE username = :username";
    public static final String INSERT_USER_QUERY = "INSERT INTO Users (username, password) VALUES (:username, :password)";

}
