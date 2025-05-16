package com.OnlineDepartmentalStore.controller;

import com.OnlineDepartmentalStore.dao.CartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AddToCartController")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddToCartController.class.getName());

    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
        logger.info("AddToCartController initialized successfully.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            int productId = Integer.parseInt(request.getParameter("product_id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (cartDAO.productExistsInCart(userId, productId)) {
                logger.info("Product already in cart: user_id=" + userId + ", product_id=" + productId);
                response.sendRedirect(request.getContextPath() + "/ProductsController?error=Product%20already%20in%20cart");
                return;
            }

            boolean success = cartDAO.addToCart(userId, productId, quantity);

            if (success) {
                logger.info("Product added to cart: user_id=" + userId + ", product_id=" + productId);
                response.sendRedirect(request.getContextPath() + "/ProductsController?message=Added%20to%20cart");
            } else {
                logger.warning("Failed to add product to cart.");
                response.sendRedirect(request.getContextPath() + "/ProductsController?error=Failed%20to%20add%20to%20cart");
            }

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid numeric input in AddToCartController", e);
            response.sendRedirect(request.getContextPath() + "/ProductsController?error=Invalid%20input");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in AddToCartController", e);
            response.sendRedirect(request.getContextPath() + "/ProductsController?error=Unexpected%20error");
        }
    }

}
