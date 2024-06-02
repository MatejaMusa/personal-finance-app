package com.matejamusa.personal_finance.query;

public class CategoryQuery {
    public static final String COUNT_CATEGORY_BY_NAME_AND_TYPE = "SELECT COUNT(*) FROM Categories WHERE name = :name AND type = :type";
    public static final String SELECT_CATEGORY_BY_NAME_AND_TYPE = "SELECT * FROM Categories WHERE name = :name AND type = :type";
    public static final String CREATE_CATEGORY = "INSERT INTO Categories (name, type) VALUES (:name, :type)";
    public static final String CREATE_USER_CATEGORY = "INSERT INTO UserCategories (user_id, category_id) VALUES (:userId, :categoryId)";
}
