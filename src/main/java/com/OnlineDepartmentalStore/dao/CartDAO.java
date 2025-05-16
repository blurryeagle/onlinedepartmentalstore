package com.OnlineDepartmentalStore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.OnlineDepartmentalStore.database.DatabaseConnection;

public class CartDAO {
	private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
	
	public boolean addToCart(int userId, int productId, int quantity) throws ClassNotFoundException {
	    String checkSql = "SELECT COUNT(*) FROM cart WHERE user_id = ? AND product_id = ?";
	    String insertSql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

	        checkStmt.setInt(1, userId);
	        checkStmt.setInt(2, productId);

	        try (var rs = checkStmt.executeQuery()) {
	            if (rs.next() && rs.getInt(1) > 0) {
	                // Product already in cart
	                return false;
	            }
	        }

	        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
	            insertStmt.setInt(1, userId);
	            insertStmt.setInt(2, productId);
	            insertStmt.setInt(3, quantity);
	            return insertStmt.executeUpdate() > 0;
	        }

	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Failed to add product to cart", e);
	        return false;
	    }
	}
	
	public boolean productExistsInCart(int userId, int productId) throws ClassNotFoundException {
	    String sql = "SELECT 1 FROM cart WHERE user_id = ? AND product_id = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        stmt.setInt(2, productId);
	        try (var rs = stmt.executeQuery()) {
	            return rs.next();
	        }

	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Failed to check if product exists in cart", e);
	        return false;
	    }
	}

}
