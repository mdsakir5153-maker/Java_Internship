package com.expansetracker;

import java.time.LocalDate;

public class Expense {
    private int id;
    private int categoryId;
    private double amount;
    private String description;
    private LocalDate expenseDate;

    // Default constructor
    public Expense() {
    }

    // Constructor without ID (for new expenses)
    public Expense(int categoryId, double amount, String description, LocalDate expenseDate) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    // Constructor with ID (for existing expenses)
    public Expense(int id, int categoryId, double amount, String description, LocalDate expenseDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Category ID: %d | Amount: ₹%.2f | Description: %s | Date: %s",
                id, categoryId, amount, description, expenseDate);
    }
}
