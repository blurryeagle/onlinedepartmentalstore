<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Category - Departmental Store</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AddCategoryStyles.css">
</head>

<body>
 	<header class="topbar">
	    <div class="brand-logo">
	      <a href="${pageContext.request.contextPath}/pages/customer/Home.jsp">
	        <img src="${pageContext.request.contextPath}/images/DMART.png" alt="DMART Logo">
	      </a>
	    </div>
	    <nav class="main-nav">
	      <ul>
	        <li><a href="${pageContext.request.contextPath}/pages/customer/Home.jsp">Home</a></li>
	        <li><a href="${pageContext.request.contextPath}/pages/admin/AddCategory.jsp">Add Category</a></li>
	        <li><a href="${pageContext.request.contextPath}/AddProductController">Add Product</a></li>
	        <li><a href="${pageContext.request.contextPath}/ProductsController">Products</a></li>
	        <li><a href="${pageContext.request.contextPath}/ProductListController">Shop</a></li>
	        <li><a href="#">Contact</a></li>
	        <li><a href="${pageContext.request.contextPath}/UserProfileController">My Account</a></li>
	      </ul>
	    </nav>
 	</header>

  <!-- Main Content -->
  <div class="form-container">
    <h2>Add New Category</h2>

    <!-- Success Message -->
    <c:if test="${not empty requestScope.successMessage}">
        <p class="alert alert-success">${requestScope.successMessage}</p>
    </c:if>
        
    <!-- Error Message -->
    <c:if test="${not empty requestScope.errorMessage}">
        <p class="alert alert-danger">${requestScope.errorMessage}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/AddCategoryController" method="post">
      <label for="name">Category Name</label>
      <input type="text" id="name" name="name" placeholder="Enter category name" required>

      <input type="submit" value="Add Category">
    </form>
  </div>

  <!-- Footer -->
  <footer class="footer">
    <p>Need help? Visit our <a href="#">Support Center</a> or <a href="#">Contact Us</a>.</p>
  </footer>
</body>
</html>
