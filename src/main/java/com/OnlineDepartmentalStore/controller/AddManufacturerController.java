package com.OnlineDepartmentalStore.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.OnlineDepartmentalStore.dao.ManufacturerDAO;
import com.OnlineDepartmentalStore.model.Manufacturer;

@WebServlet("/AddManufacturerController")
public class AddManufacturerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddManufacturerController.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String manufacturerName = request.getParameter("name");

        logger.info("Received request to add category: " + manufacturerName);

        try {
            Manufacturer manufacturer = new Manufacturer(0, manufacturerName); // ID is auto-generated
            ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

            boolean isAdded = manufacturerDAO.addManufacturer(manufacturer);

            if (isAdded) {
                logger.info("Manufacturer added successfully: " + manufacturerName);
                request.setAttribute("successMessage", "Manufacturer added successfully.");
            } else {
                logger.warning("Manufacturer already exists: " + manufacturerName);
                request.setAttribute("errorMessage", "Manufacturer already exists. Please choose a different name.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding manufacturer: " + manufacturerName, e);
            request.setAttribute("errorMessage", "An error occurred while adding the manufacturer.");
        }

        request.getRequestDispatcher("/pages/admin/AddManufacturer.jsp").forward(request, response);
    }
}
