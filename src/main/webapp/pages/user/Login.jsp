<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Login - Departmental Store</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/LoginStyles.css">
</head>

<body>
  <header class="login-header">
    <div class="logo">
      <a href="Login.jsp">
        <img src="${pageContext.request.contextPath}/images/DMART.png" alt="Logo">
      </a>
    </div>
  </header>

  <div class="login-container">
    <div class="login-form">
      <h1>Welcome Back</h1>

      <!-- Display Error Message (if any) -->
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
      </c:if>
      
      <!-- Display Success Message (if any) -->
      <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/LoginController" method="POST">
        <label for="email">Email Address</label>
        <input type="email" id="email" name="email" placeholder="Enter your email" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter your password" required />

        <button type="submit">Log In</button>
      </form>

      <div class="signup-link">
        Don’t have an account? <a href="${pageContext.request.contextPath}/pages/customer/Registration.jsp">Create one here</a>
      </div>
    </div>

    <footer class="footer">
      <p>By logging in, you agree to our <a href="#">Terms of Use</a> and <a href="#">Privacy Policy</a>.</p>
    </footer>
  </div>
</body>
</html>
