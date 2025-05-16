<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>DMART - Online Departmental Store</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HomeStyles.css" />
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
        <li><a href="${pageContext.request.contextPath}/pages/admin/AddManufacturer.jsp">Add Manufacturer</a></li>
        <li><a href="${pageContext.request.contextPath}/AddProductController">Add Product</a></li>
        <li><a href="${pageContext.request.contextPath}/ProductsController">Products</a></li>
        <li><a href="${pageContext.request.contextPath}/pages/customer/Cart.jsp">Cart</a></li>
        <li><a href="${pageContext.request.contextPath}/ProductListController">Shop</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="${pageContext.request.contextPath}/UserProfileController">My Account</a></li>
      </ul>
    </nav>
  </header>

  <main class="welcome-section">
    <div class="intro-text">
      <h1>Shop Smarter, Live Better</h1>
      <p>Your one-stop online departmental store for groceries, daily needs, and more. Browse products and place orders from the comfort of your home.</p>
      <a class="action-btn" href="${pageContext.request.contextPath}/ProductSearch.jsp">Start Shopping</a>
    </div>
    <div class="intro-image">
      <img src="${pageContext.request.contextPath}/images/ShoppingIllustration.png" alt="Shopping Illustration">
    </div>
  </main>

  <footer class="site-footer">
    <p>&copy; 2025 DMart. All rights reserved.</p>
  </footer>
</body>
</html>
