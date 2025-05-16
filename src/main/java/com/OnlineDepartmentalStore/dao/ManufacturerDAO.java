package com.OnlineDepartmentalStore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.OnlineDepartmentalStore.database.DatabaseConnection;
import com.OnlineDepartmentalStore.model.Manufacturer;

public class ManufacturerDAO {
	private static final Logger logger = Logger.getLogger(ManufacturerDAO.class.getName());

    // Method to add a new manufacturer
    public boolean addManufacturer(Manufacturer manufacturer) throws ClassNotFoundException {
        String sqlInsert = "INSERT INTO manufacturer (name) VALUES (?)";
        String sqlCheck = "SELECT COUNT(*) FROM manufacturer WHERE name = ?";  // Check if manufacturer already exists

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
             
            // Check if the manufacturer already exists
            checkStmt.setString(1, manufacturer.getName());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Manufacturer already exists
                logger.warning("Manufacturer already exists: " + manufacturer.getName());
                return false; 
            }

            // If manufacturer does not exist, insert it
            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setString(1, manufacturer.getName());
                int rowsInserted = insertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    logger.info("Manufacturer inserted successfully.");
                    return true;
                } else {
                    logger.warning("Failed to insert manufacturer.");
                    return false;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL error while adding manufacturer: " + manufacturer.getName(), e);
            return false; // Return false on error
        }
    }
    
 // Method to get all categories
    public List<Manufacturer> getAllManufacturers() throws ClassNotFoundException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String sql = "SELECT manufacturer_id, name FROM manufacturer";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerId(rs.getInt("manufacturer_id"));
                manufacturer.setName(rs.getString("name"));
                manufacturers.add(manufacturer);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching manufacturers", e);
        }

        return manufacturers;
    }
    
}
