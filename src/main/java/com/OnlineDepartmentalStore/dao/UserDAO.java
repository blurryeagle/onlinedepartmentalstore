package com.OnlineDepartmentalStore.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.OnlineDepartmentalStore.database.DatabaseConnection;
import com.OnlineDepartmentalStore.model.User;
import com.OnlineDepartmentalStore.utility.EncryptionDecryption;

public class UserDAO {
    
    // Logger instance
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public boolean registerUser(User user) throws ClassNotFoundException {
        String checkQuery = "SELECT * FROM user WHERE email = ?";
        String insertQuery = "INSERT INTO user (username, email, password, role) VALUES (?, ?, ?, ?)";
        
        logger.info("Attempting to register user: " + user.getEmail());
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, user.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                logger.warning("Email already registered: " + user.getEmail());
                return false; // Email already registered
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, user.getUsername());
                insertStmt.setString(2, user.getEmail());
                insertStmt.setString(3, user.getPassword());
                insertStmt.setString(4, user.getRole());

                boolean isRegistered = insertStmt.executeUpdate() > 0;
                
                if (isRegistered) {
                    logger.info("User registered successfully: " + user.getEmail());
                } else {
                    logger.warning("Failed to register user: " + user.getEmail());
                }
                
                return isRegistered;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error during user registration", e);
            return false;
        }
    }

    public User loginUser(String email, String inputPassword) throws SQLException, ClassNotFoundException {
        String loginQuery = "SELECT * FROM user WHERE email = ?";

        logger.info("Attempting login for user: " + email);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(loginQuery)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");

                try {
                    // Decrypt the stored password
                    String decryptedPassword = EncryptionDecryption.decrypt(encryptedPassword);

                    // Compare user input password with decrypted password
                    if (inputPassword.equals(decryptedPassword)) {
                        logger.info("Password match for user: " + email);

                        User user = new User();
                        user.setUser_id(rs.getInt("user_id")); 
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(encryptedPassword); 
                        user.setRole(rs.getString("role"));
                        return user;
                    } else {
                        logger.warning("Password mismatch for user: " + email);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error decrypting password for user: " + email, e);
                }
            } else {
                logger.warning("No user found with email: " + email);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error during login for user: " + email, e);
            throw e; // Let caller handle it
        }

        return null;
    }
    
}
