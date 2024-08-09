package com.matejamusa.personal_finance.query;

public class CategoryQuery {
    public static final String COUNT_CATEGORY_BY_NAME_AND_TYPE_QUERY = "SELECT COUNT(*) FROM Categories WHERE name = :name AND type = :type";
    public static final String COUNT_CATEGORY_BY_NAME_QUERY = "SELECT COUNT(*) FROM Categories WHERE name = :name";
    public static final String SELECT_CATEGORY_BY_NAME_AND_TYPE_QUERY = "SELECT * FROM Categories WHERE name = :name AND type = :type";
    public static final String CREATE_CATEGORY_QUERY = "INSERT INTO Categories (name, type) VALUES (:name, :type)";
    public static final String CREATE_USER_CATEGORY_QUERY = "INSERT INTO UserCategories (user_id, category_id) VALUES (:userId, :categoryId)";
    public static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM Categories WHERE id = :id";
    public static final String COUNT_USER_CATEGORY_BY_USER_ID_AND_CATEGORY_ID_QUERY = "SELECT COUNT(*) FROM UserCategories WHERE user_id = :userId AND category_id = :categoryId";
    public static final String SELECT_ALL_CATEGORIES_FOR_USER_ID_QUERY = "SELECT * FROM Categories JOIN UserCategories ON Categories.id = UserCategories.category_id WHERE UserCategories.user_id = :userId";
}
