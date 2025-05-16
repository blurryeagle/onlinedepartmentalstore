package com.OnlineDepartmentalStore.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.OnlineDepartmentalStore.database.DatabaseConnection;
import com.OnlineDepartmentalStore.model.Product;

public class ProductDAO {
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    public int addProduct(Product product) throws ClassNotFoundException {
        String sqlCheck = "SELECT COUNT(*) FROM product WHERE name = ?";
        String sqlInsert = "INSERT INTO product (name, price, stock_quantity, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setString(1, product.getName());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                logger.warning("Product already exists: " + product.getName());
                return -1;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, product.getName());
                insertStmt.setDouble(3, product.getPrice());
                insertStmt.setInt(4, product.getStock_quantity());
                insertStmt.setInt(5, product.getCategory_id());

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet keys = insertStmt.getGeneratedKeys();
                    if (keys.next()) {
                        int generatedId = keys.getInt(1);
                        logger.info("Product added with ID: " + generatedId);
                        return generatedId;
                    }
                }
                return -1;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error while adding product: " + product.getName(), e);
            return -1;
        }
    }

    
    public boolean updateProductImage(int productId, String imagePath) throws ClassNotFoundException {
        String sqlUpdate = "UPDATE product SET product_image_path = ? WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setString(1, imagePath);
            stmt.setInt(2, productId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error while updating image for product ID: " + productId, e);
            return false;
        }
    }

    public Map<String, List<Product>> getAvailableProductsByCategory() throws ClassNotFoundException {
        String sql = "SELECT p.product_id, p.name, p.price, p.stock_quantity, " +
                	 "p.category_id, p.manufacturer_id, p.product_image_path, " +
                     "c.name AS category_name, m.name AS manufacturer_name " +
                     "FROM product p " +
                     "JOIN category c ON p.category_id = c.category_id " +
                     "JOIN manufacturer m ON p.manufacturer_id = m.manufacturer_id " +
                     "WHERE p.stock_quantity > 0";

        Map<String, List<Product>> categoryProducts = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity"),
                    rs.getInt("category_id"),
                    rs.getString("manufacturer_name"),
                    rs.getString("product_image_path")
                );

                categoryProducts
                    .computeIfAbsent(categoryName, k -> new ArrayList<>())
                    .add(product);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch products by category", e);
        }

        return categoryProducts;
    }

}
