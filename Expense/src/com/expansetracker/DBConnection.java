package com.expansetracker;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;
    private static Dotenv dotenv = null;

    // Load environment variables
    static {
        try {
            dotenv = Dotenv.configure()
                    .directory(".")
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }
    }

    // Get database connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Get database credentials from environment variables
                String url = dotenv.get("DB_URL", "jdbc:mysql://localhost:3306/expense_tracker");
                String user = dotenv.get("DB_USER", "root");
                String password = dotenv.get("DB_PASSWORD", "SAKIR@123");

                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully!");

            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Database connection failed: " + e.getMessage());
                System.err.println("Please check your .env file and ensure MySQL is running.");
            }
        }
        return connection;
    }

    // Close database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
