package com.expansetracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ExpenseDAO expenseDAO = new ExpenseDAO();
    private static CategoryDAO categoryDAO = new CategoryDAO();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   EXPENSE TRACKER - CONSOLE APP       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        // Test database connection
        if (DBConnection.getConnection() == null) {
            System.err.println("Failed to connect to database. Exiting...");
            return;
        }

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    viewExpensesByCategory();
                    break;
                case 4:
                    viewExpensesByDateRange();
                    break;
                case 5:
                    deleteExpense();
                    break;
                case 6:
                    manageCategories();
                    break;
                case 7:
                    viewExpenseSummary();
                    break;
                case 8:
                    System.out.println("\nThank you for using Expense Tracker!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        DBConnection.closeConnection();
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           MAIN MENU                    ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Add Expense                         ║");
        System.out.println("║ 2. View All Expenses                   ║");
        System.out.println("║ 3. View Expenses by Category           ║");
        System.out.println("║ 4. View Expenses by Date Range         ║");
        System.out.println("║ 5. Delete Expense                      ║");
        System.out.println("║ 6. Manage Categories                   ║");
        System.out.println("║ 7. View Expense Summary                ║");
        System.out.println("║ 8. Exit                                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private static void addExpense() {
        System.out.println("\n=== ADD NEW EXPENSE ===");

        // Show available categories
        displayCategories();

        int categoryId = getIntInput("Enter Category ID: ");
        if (!categoryDAO.categoryExists(categoryId)) {
            System.out.println("Invalid category ID!");
            return;
        }

        double amount = getDoubleInput("Enter Amount: ₹");
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        LocalDate date = getDateInput("Enter Date (yyyy-MM-dd) or press Enter for today: ");

        Expense expense = new Expense(categoryId, amount, description, date);

        if (expenseDAO.addExpense(expense)) {
            System.out.println("✓ Expense added successfully!");
        } else {
            System.out.println("✗ Failed to add expense.");
        }
    }

    private static void viewAllExpenses() {
        System.out.println("\n=== ALL EXPENSES ===");
        List<Expense> expenses = expenseDAO.getAllExpenses();

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("─────────────────────────────────────────────────────────────────────────");
        for (Expense expense : expenses) {
            expenseDAO.displayExpense(expense);
        }
        System.out.println("─────────────────────────────────────────────────────────────────────────");
        System.out.printf("Total Expenses: ₹%.2f%n", expenseDAO.getTotalExpenses());
    }

    private static void viewExpensesByCategory() {
        System.out.println("\n=== EXPENSES BY CATEGORY ===");
        displayCategories();

        int categoryId = getIntInput("Enter Category ID: ");
        if (!categoryDAO.categoryExists(categoryId)) {
            System.out.println("Invalid category ID!");
            return;
        }

        List<Expense> expenses = expenseDAO.getExpensesByCategory(categoryId);

        if (expenses.isEmpty()) {
            System.out.println("No expenses found for this category.");
            return;
        }

        String categoryName = categoryDAO.getCategoryName(categoryId);
        System.out.println("\nCategory: " + categoryName);
        System.out.println("─────────────────────────────────────────────────────────────────────────");
        for (Expense expense : expenses) {
            expenseDAO.displayExpense(expense);
        }
        System.out.println("─────────────────────────────────────────────────────────────────────────");
        System.out.printf("Total: ₹%.2f%n", expenseDAO.getTotalExpensesByCategory(categoryId));
    }

    private static void viewExpensesByDateRange() {
        System.out.println("\n=== EXPENSES BY DATE RANGE ===");

        LocalDate startDate = getDateInput("Enter Start Date (yyyy-MM-dd): ");
        LocalDate endDate = getDateInput("Enter End Date (yyyy-MM-dd): ");

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date!");
            return;
        }

        List<Expense> expenses = expenseDAO.getExpensesByDateRange(startDate, endDate);

        if (expenses.isEmpty()) {
            System.out.println("No expenses found in this date range.");
            return;
        }

        System.out.printf("\nExpenses from %s to %s:%n", startDate, endDate);
        System.out.println("─────────────────────────────────────────────────────────────────────────");
        double total = 0;
        for (Expense expense : expenses) {
            expenseDAO.displayExpense(expense);
            total += expense.getAmount();
        }
        System.out.println("─────────────────────────────────────────────────────────────────────────");
        System.out.printf("Total: ₹%.2f%n", total);
    }

    private static void deleteExpense() {
        System.out.println("\n=== DELETE EXPENSE ===");
        viewAllExpenses();

        int expenseId = getIntInput("\nEnter Expense ID to delete (0 to cancel): ");

        if (expenseId == 0) {
            System.out.println("Deletion cancelled.");
            return;
        }

        System.out.print("Are you sure you want to delete this expense? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            if (expenseDAO.deleteExpense(expenseId)) {
                System.out.println("✓ Expense deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete expense. Invalid ID?");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void manageCategories() {
        System.out.println("\n=== MANAGE CATEGORIES ===");
        System.out.println("1. View All Categories");
        System.out.println("2. Add New Category");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                displayCategories();
                break;
            case 2:
                addCategory();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void displayCategories() {
        List<String[]> categories = categoryDAO.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.printf("%-5s %-20s %-30s%n", "ID", "Name", "Description");
        System.out.println("─────────────────────────────────────────────────────────");
        for (String[] category : categories) {
            System.out.printf("%-5s %-20s %-30s%n", category[0], category[1], category[2]);
        }
        System.out.println("─────────────────────────────────────────────────────────");
    }

    private static void addCategory() {
        System.out.println("\n=== ADD NEW CATEGORY ===");
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Category Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        if (categoryDAO.addCategory(name, description)) {
            System.out.println("✓ Category added successfully!");
        } else {
            System.out.println("✗ Failed to add category. Name might already exist.");
        }
    }

    private static void viewExpenseSummary() {
        System.out.println("\n=== EXPENSE SUMMARY ===");

        List<String[]> categories = categoryDAO.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("%-20s %15s%n", "Category", "Total Amount");
        System.out.println("─────────────────────────────────────────────────────────");

        double grandTotal = 0;
        for (String[] category : categories) {
            int categoryId = Integer.parseInt(category[0]);
            String categoryName = category[1];
            double total = expenseDAO.getTotalExpensesByCategory(categoryId);

            if (total > 0) {
                System.out.printf("%-20s %15.2f%n", categoryName, total);
                grandTotal += total;
            }
        }

        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("%-20s %15.2f%n", "GRAND TOTAL", grandTotal);
        System.out.println("─────────────────────────────────────────────────────────");
    }

    // Helper methods for input validation
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return LocalDate.now();
                }

                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2026-01-28)");
            }
        }
    }
}
