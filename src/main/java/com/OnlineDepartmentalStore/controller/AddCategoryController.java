package com.OnlineDepartmentalStore.controller;

import com.OnlineDepartmentalStore.dao.CategoryDAO;
import com.OnlineDepartmentalStore.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AddCategoryController")
public class AddCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddCategoryController.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryName = request.getParameter("name");

        logger.info("Received request to add category: " + categoryName);

        try {
            Category category = new Category(0, categoryName); // ID is auto-generated
            CategoryDAO categoryDAO = new CategoryDAO();

            boolean isAdded = categoryDAO.addCategory(category);

            if (isAdded) {
                logger.info("Category added successfully: " + categoryName);
                request.setAttribute("successMessage", "Category added successfully.");
            } else {
                logger.warning("Category already exists: " + categoryName);
                request.setAttribute("errorMessage", "Category already exists. Please choose a different name.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding category: " + categoryName, e);
            request.setAttribute("errorMessage", "An error occurred while adding the category.");
        }

        request.getRequestDispatcher("/pages/admin/AddCategory.jsp").forward(request, response);
    }
}
