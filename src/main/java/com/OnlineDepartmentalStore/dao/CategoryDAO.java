package com.OnlineDepartmentalStore.dao;

import com.OnlineDepartmentalStore.database.DatabaseConnection;
import com.OnlineDepartmentalStore.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDAO {

    private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());

    // Method to add a new category
    public boolean addCategory(Category category) throws ClassNotFoundException {
        String sqlInsert = "INSERT INTO category (name) VALUES (?)";
        String sqlCheck = "SELECT COUNT(*) FROM category WHERE name = ?";  // Check if category already exists

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
             
            // Check if the category already exists
            checkStmt.setString(1, category.getName());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Category already exists
                logger.warning("Category already exists: " + category.getName());
                return false; // Or throw an exception if you want to handle it at the controller level
            }

            // If category does not exist, insert it
            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setString(1, category.getName());
                int rowsInserted = insertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    logger.info("Category inserted successfully.");
                    return true;
                } else {
                    logger.warning("Failed to insert category.");
                    return false;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error while adding category: " + category.getName(), e);
            return false; // Return false on error
        }
    }
    
    // Method to get all categories
    public List<Category> getAllCategories() throws ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_id, name FROM category";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching categories", e);
        }

        return categories;
    }

}
