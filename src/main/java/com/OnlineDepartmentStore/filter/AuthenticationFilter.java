package com.OnlineDepartmentStore.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Ensures that only authenticated users can access protected pages.
 */
@WebFilter(urlPatterns = {"/pages/*"}) // Adjust path as needed
public class AuthenticationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Departmental Store AuthenticationFilter initialized");
    }

    @Override
    public void destroy() {
        logger.info("Departmental Store AuthenticationFilter destroyed");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);

        String path = httpReq.getRequestURI();
        boolean userLoggedIn = (session != null && session.getAttribute("user") != null);

        // Pages that should not be blocked even when not logged in
        boolean isPublicResource = path.endsWith("Login.jsp") || 
                                   path.endsWith("Registration.jsp") || 
                                   path.endsWith("LoginController") || 
                                   path.endsWith("RegistrationController");

        if (userLoggedIn || isPublicResource) {
            logger.fine("Access granted for: " + path);
            chain.doFilter(request, response); // Allow the request to proceed
        } else {
            logger.warning("Unauthorized access attempt to: " + path);
            httpRes.sendRedirect(httpReq.getContextPath() + "/pages/user/Login.jsp");
        }
    }
}
