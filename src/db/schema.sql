CREATE SCHEMA IF NOT EXISTS personalFinance;

SET NAMES 'UTF8MB4';
SET TIME_ZONE = '+1:00';

USE personalFinance;

CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
GRANT ALL PRIVILEGES ON personalFinance.* TO '${MYSQL_USER}'@'%';
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS UserCategories;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Users_Email UNIQUE (username)
);

CREATE TABLE Categories
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('EXPENSE','INCOME')),
    CONSTRAINT UQ_Categories_Name_Type UNIQUE (name, type)
);

CREATE TABLE UserCategories
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES Categories (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Accounts
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(100) DEFAULT NULL,
    description VARCHAR(200) DEFAULT NULL,
    priority VARCHAR(50) NOT NULL CHECK (priority IN ('HIGH','MEDIUM','LOW')),
    balance DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Transactions
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES Categories (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO Users (username, password) VALUES ('Matejamusa', 'Matejamusa123!');

INSERT INTO Categories (name, type) VALUES
                                        ('Namirnice', 'EXPENSE'),
                                        ('Racuni', 'EXPENSE'),
                                        ('Plata', 'INCOME'),
                                        ('Vecera', 'EXPENSE'),
                                        ('Prevoz', 'EXPENSE'),
                                        ('Zabava', 'EXPENSE'),
                                        ('Pokloni', 'INCOME'),
                                        ('Investicije', 'INCOME');

INSERT INTO UserCategories (user_id, category_id)
SELECT 1, id FROM Categories;

INSERT INTO Accounts (user_id, name, description, priority, balance) VALUES
                                                                         (1, 'Glavni racun', 'Glavni racun za trosenje', 'HIGH', -11500.00),
                                                                         (1, 'Stedni racun', 'Racun za stednju', 'MEDIUM', 15000.00),
                                                                         (1, 'Kreditna kartica', 'Racun za kreditnu karticu', 'LOW', -7000.00);

INSERT INTO Transactions (account_id, category_id, amount, transaction_date) VALUES
                                                                                 (1, 1, 1000.00, '2024-08-01 10:00:00'),
                                                                                 (1, 2, 6000.00, '2024-08-02 09:00:00'),
                                                                                 (1, 4, 2000.00, '2024-08-05 09:00:00'),
                                                                                 (1, 5, 1500.00, '2024-08-07 19:45:00'),
                                                                                 (1, 6, 1000.00, '2024-08-10 08:15:00');

INSERT INTO Transactions (account_id, category_id, amount, transaction_date) VALUES
                                                                                 (2, 7, 5000.00, '2024-08-06 16:00:00'),
                                                                                 (2, 8, 10000.00, '2024-08-15 11:30:00');

INSERT INTO Transactions (account_id, category_id, amount, transaction_date) VALUES
                                                                                 (3, 2, 4000.00, '2024-08-12 20:00:00'),
                                                                                 (3, 6, 1000.00, '2024-08-18 15:45:00'),
                                                                                 (3, 1, 2000.00, '2024-08-20 11:00:00');