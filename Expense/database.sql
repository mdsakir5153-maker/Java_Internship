-- Expense Tracker Database Schema
-- Create database
CREATE DATABASE IF NOT EXISTS expense_tracker;
USE expense_tracker;

-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Expenses table
CREATE TABLE IF NOT EXISTS expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description TEXT,
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Insert sample categories
INSERT INTO categories (name, description) VALUES
('Food', 'Food and dining expenses'),
('Transportation', 'Travel and commute expenses'),
('Entertainment', 'Movies, games, and leisure activities'),
('Utilities', 'Electricity, water, internet bills'),
('Healthcare', 'Medical and health-related expenses'),
('Shopping', 'Clothing and general shopping');

-- Insert sample expenses
INSERT INTO expenses (category_id, amount, description, expense_date) VALUES
(1, 250.00, 'Grocery shopping', '2026-01-25'),
(1, 150.00, 'Restaurant dinner', '2026-01-26'),
(2, 100.00, 'Fuel', '2026-01-27'),
(3, 500.00, 'Movie tickets', '2026-01-28'),
(4, 1200.00, 'Electricity bill', '2026-01-20'),
(5, 800.00, 'Doctor consultation', '2026-01-22');
