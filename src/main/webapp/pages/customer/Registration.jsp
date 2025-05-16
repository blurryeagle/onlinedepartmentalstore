<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Sign Up - Departmental Store</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/RegistrationStyles.css">
</head>

<body>
  <header class="login-header">
    <div class="logo">
      <a href="${pageContext.request.contextPath}/pages/user/Login.jsp">
        <img src="${pageContext.request.contextPath}/images/DMART.png" alt="Logo">
      </a>
    </div>
  </header>

  <div class="login-container">
    <div class="login-form">
      <h1>Create Your Account</h1>

      <!-- Display Error Message (if any) -->
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
      </c:if>
      
      <!-- Display Success Message (if any) -->
      <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/RegistrationController" method="POST">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter your username" required />

        <label for="email">Email Address</label>
        <input type="email" id="email" name="email" placeholder="Enter your email" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter your password" required />

        <button type="submit">Sign Up</button>
      </form>

      <div class="login-link">
        Already have an account? <a href="${pageContext.request.contextPath}/pages/user/Login.jsp">Log in here</a>
      </div>
    </div>

    <footer class="footer">
      <p>By signing up, you agree to our <a href="#">Terms of Use</a> and <a href="#">Privacy Policy</a>.</p>
    </footer>
  </div>
</body>
</html>
