package com.expansetracker;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private CategoryDAO categoryDAO = new CategoryDAO();

    // Add a new expense
    public boolean addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (category_id, amount, description, expense_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expense.getCategoryId());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getDescription());
            pstmt.setDate(4, Date.valueOf(expense.getExpenseDate()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding expense: " + e.getMessage());
            return false;
        }
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, category_id, amount, description, expense_date FROM expenses ORDER BY expense_date DESC";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("expense_date").toLocalDate());
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expenses: " + e.getMessage());
        }

        return expenses;
    }

    // Get expenses by category
    public List<Expense> getExpensesByCategory(int categoryId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, category_id, amount, description, expense_date FROM expenses WHERE category_id = ? ORDER BY expense_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("expense_date").toLocalDate());
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expenses by category: " + e.getMessage());
        }

        return expenses;
    }

    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, category_id, amount, description, expense_date FROM expenses WHERE expense_date BETWEEN ? AND ? ORDER BY expense_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("expense_date").toLocalDate());
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expenses by date range: " + e.getMessage());
        }

        return expenses;
    }

    // Delete expense by ID
    public boolean deleteExpense(int expenseId) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expenseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    // Get total expenses
    public double getTotalExpenses() {
        String sql = "SELECT SUM(amount) as total FROM expenses";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating total expenses: " + e.getMessage());
        }

        return 0.0;
    }

    // Get total expenses by category
    public double getTotalExpensesByCategory(int categoryId) {
        String sql = "SELECT SUM(amount) as total FROM expenses WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating category expenses: " + e.getMessage());
        }

        return 0.0;
    }

    // Display expense with category name
    public void displayExpense(Expense expense) {
        String categoryName = categoryDAO.getCategoryName(expense.getCategoryId());
        System.out.printf("ID: %d | Category: %s | Amount: ₹%.2f | Description: %s | Date: %s%n",
                expense.getId(), categoryName, expense.getAmount(),
                expense.getDescription(), expense.getExpenseDate());
    }
}
