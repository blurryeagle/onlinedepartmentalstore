<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Product - Departmental Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AddProductStyles.css">
</head>

<body>
    <header class="topbar">
	    <div class="brand-logo">
	      <a href="${pageContext.request.contextPath}/pages/user/Home.jsp">
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
        <h2>Add New Product</h2>

        <!-- Success Message -->
		<c:if test="${not empty message}">
		    <p class="alert alert-success">${message}</p>
		</c:if>
		
		<!-- Error Message -->
		<c:if test="${not empty errorMessage}">
		    <p class="alert alert-danger">${errorMessage}</p>
		</c:if>

        <!-- Product Form -->
        <form action="${pageContext.request.contextPath}/AddProductController" method="post" enctype="multipart/form-data">
            <label for="name">Product Name</label>
            <input type="text" id="name" name="name" placeholder="Enter product name" required>

            <label for="price">Price</label>
            <input type="number" id="price" name="price" step="0.01" placeholder="Enter product price" required>

            <label for="stock_quantity">Stock Quantity</label>
            <input type="number" id="stock_quantity" name="stock_quantity" placeholder="Enter stock quantity" required>

            <label for="category_id">Category</label>
            <select id="category_id" name="category_id" required>
                <option value="">Select Category</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.name}</option>
                </c:forEach>
            </select>
            
            <label for="manufacturer_id">Manufacturer</label>
            <select id="manufacturer_id" name="manufacturer_id" required>
                <option value="">Select Manufacturer</option>
                <c:forEach var="manufacturer" items="${manufacturers}">
                    <option value="${manufacturer.manufacturerId}">${manufacturer.name}</option>
                </c:forEach>
            </select>

            <label for="product_image">Product Image</label>
            <input type="file" id="product_image" name="product_image" accept="image/*" required>

            <input type="submit" value="Add Product">
        </form>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p>Need help? Visit our <a href="#">Support Center</a> or <a href="#">Contact Us</a>.</p>
    </footer>
</body>
</html>
