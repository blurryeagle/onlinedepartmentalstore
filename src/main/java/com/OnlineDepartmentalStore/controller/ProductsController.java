package com.OnlineDepartmentalStore.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.OnlineDepartmentalStore.dao.ProductDAO;
import com.OnlineDepartmentalStore.model.Product;
import com.OnlineDepartmentalStore.model.User;

@WebServlet("/ProductsController")
public class ProductsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductsController.class.getName());

	private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
        	User user = (User) request.getSession().getAttribute("user");
        	if (user != null) {
        	    logger.info("User found in session: " + user.getEmail() + ", ID: " + user.getUser_id());
        	    request.setAttribute("user", user);
        	} else {
        	    logger.warning("No user found in session!");
        	}
        	
        	// Handle optional message or error passed via URL
            String message = request.getParameter("message");
            String error = request.getParameter("error");

            if (message != null) request.setAttribute("message", message);
            if (error != null) request.setAttribute("error", error);
            
            Map<String, List<Product>> productsByCategory = productDAO.getAvailableProductsByCategory();
            request.setAttribute("productsByCategory", productsByCategory);
            request.getRequestDispatcher("/pages/customer/Products.jsp").forward(request, response);

        } catch (ClassNotFoundException e) {
            request.setAttribute("errorMessage", "Unable to load products.");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }
}
