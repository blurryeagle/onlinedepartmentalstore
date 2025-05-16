package com.OnlineDepartmentalStore.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.OnlineDepartmentalStore.dao.UserDAO;
import com.OnlineDepartmentalStore.model.User;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("LoginController initialized");
        userDAO = new UserDAO();  // Initialize DAO instance here if reused
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pages/user/Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.loginUser(email, password);

            if (user != null) {
                HttpSession existingSession = request.getSession(false);
                if (existingSession != null) {
                    existingSession.invalidate();
                }

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                logger.info("User login successful: " + email);

                String role = user.getRole();
                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/pages/admin/Dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/pages/customer/Home.jsp");
                }

            } else {
                logger.warning("Login failed for email: " + email);
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.setAttribute("enteredEmail", email);
                request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Login error", ex);
            request.setAttribute("errorMessage", "An internal error occurred. Please try again later.");
            request.getRequestDispatcher("/pages/user/Login.jsp").forward(request, response);
        }
    }
}
