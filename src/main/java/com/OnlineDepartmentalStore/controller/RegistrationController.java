package com.OnlineDepartmentalStore.controller;

import com.OnlineDepartmentalStore.dao.UserDAO;
import com.OnlineDepartmentalStore.model.User;
import com.OnlineDepartmentalStore.utility.EncryptionDecryption;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Encrypt the password before saving
            String encryptedPassword = EncryptionDecryption.encrypt(password);

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(encryptedPassword);
            // No need to set role here, as "customer" is the default in the database

            boolean isRegistered = userDAO.registerUser(user);

            if (isRegistered) {
                request.setAttribute("successMessage", "Registration successful! Please log in.");
                // Forward to login page (or wherever you want the user to go after successful registration)
                request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Email may already be in use.");
                // Forward to registration page for user to try again
                request.getRequestDispatcher("/pages/customer/Registration.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Log the error with the exception details
            logger.log(Level.SEVERE, "Error during registration", e);
            request.setAttribute("errorMessage", "A system error occurred. Please try again later.");
            // Forward to registration page for error message
            request.getRequestDispatcher("/pages/customer/Registration.jsp").forward(request, response);
        }
    }
}
