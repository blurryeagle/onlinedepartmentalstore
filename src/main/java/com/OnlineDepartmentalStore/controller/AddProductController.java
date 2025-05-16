package com.OnlineDepartmentalStore.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.OnlineDepartmentalStore.dao.CategoryDAO;
import com.OnlineDepartmentalStore.dao.ManufacturerDAO;
import com.OnlineDepartmentalStore.dao.ProductDAO;
import com.OnlineDepartmentalStore.model.Category;
import com.OnlineDepartmentalStore.model.Manufacturer;
import com.OnlineDepartmentalStore.model.Product;
import com.OnlineDepartmentalStore.utility.ProductImageUtility;

@WebServlet("/AddProductController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 15 // 15MB
)
public class AddProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddProductController.class.getName());

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ManufacturerDAO manufacturerDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        manufacturerDAO = new ManufacturerDAO();
        logger.info("AddProductController initialized successfully.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String productName = request.getParameter("name");
            double productPrice = Double.parseDouble(request.getParameter("price"));
            int quantityInStock = Integer.parseInt(request.getParameter("stock_quantity"));
            int categoryIdentifier = Integer.parseInt(request.getParameter("category_id"));
            int manufacturerIdentifier = Integer.parseInt(request.getParameter("manufacturer_id"));

            Part imagePart = request.getPart("product_image");
            String imagePath = ProductImageUtility.saveProductImage(imagePart, getServletContext());

            // Initially save product without image path
            Product newProduct = new Product(productName, productPrice, quantityInStock, categoryIdentifier, manufacturerIdentifier, null);

            int productId = productDAO.addProduct(newProduct);

            String redirectUrl = request.getContextPath() + "/AddProductController";

            if (productId > 0) {
                boolean imageUpdated = productDAO.updateProductImage(productId, imagePath);
                if (imageUpdated) {
                    logger.info("Product image path updated successfully for ID: " + productId);
                } else {
                    logger.warning("Product image update failed for ID: " + productId);
                }

                response.sendRedirect(redirectUrl + "?message=Product%20added%20successfully");
            } else {
                response.sendRedirect(redirectUrl + "?error=Product%20already%20exists");
            }

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Error parsing number values", e);
            response.sendRedirect(request.getContextPath() + "/AddProductController?error=Invalid%20input%20for%20price%20or%20stock%20quantity");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred in AddProductHandler", e);
            response.sendRedirect(request.getContextPath() + "/AddProductController?error=An%20unexpected%20error%20occurred");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            logger.info("Categories loaded successfully.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load categories", e);
            request.setAttribute("errorMessage", "Unable to load categories.");
        }
        
        try {
            List<Manufacturer> manufacturers = manufacturerDAO.getAllManufacturers();
            request.setAttribute("manufacturers", manufacturers);
            logger.info("Manufacturers loaded successfully.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load manufacturers", e);
            request.setAttribute("errorMessage", "Unable to load manufacturers.");
        }

        String message = request.getParameter("message");
        String error = request.getParameter("error");

        if (message != null) {
            request.setAttribute("message", message);
        }

        if (error != null) {
            request.setAttribute("errorMessage", error);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/admin/AddProduct.jsp");
        dispatcher.forward(request, response);
    }
}
