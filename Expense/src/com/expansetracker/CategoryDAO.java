package com.expansetracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Add a new category
    public boolean addCategory(String name, String description) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
            return false;
        }
    }

    // Get all categories
    public List<String[]> getAllCategories() {
        List<String[]> categories = new ArrayList<>();
        String sql = "SELECT id, name, description FROM categories ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] category = new String[3];
                category[0] = String.valueOf(rs.getInt("id"));
                category[1] = rs.getString("name");
                category[2] = rs.getString("description");
                categories.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching categories: " + e.getMessage());
        }

        return categories;
    }

    // Get category name by ID
    public String getCategoryName(int categoryId) {
        String sql = "SELECT name FROM categories WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching category name: " + e.getMessage());
        }

        return "Unknown";
    }

    // Check if category exists
    public boolean categoryExists(int categoryId) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking category: " + e.getMessage());
        }

        return false;
    }
}
