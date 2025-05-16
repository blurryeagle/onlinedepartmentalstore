<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ProductsStyles.css">
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
                <li><a href="#">Shop</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="${pageContext.request.contextPath}/UserProfileController">My Account</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <h2>Our Products</h2><br>

        <c:if test="${not empty message}">
            <p class="alert alert-success">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="alert alert-danger">${error}</p>
        </c:if>

        <c:forEach var="entry" items="${productsByCategory}">
            <div class="category-section">
                <h3 class="category-title">${entry.key}</h3>

                <div class="product-list">
                    <c:forEach var="product" items="${entry.value}">
                        <div class="product-card">
                            <div class="product-image-wrapper">
    							<img src="${pageContext.request.contextPath}/${product.product_image_path}" class="product-image" alt="${product.name}">
							</div>

                            <h4>${product.name}</h4>
                            <p><strong>Rs ${product.price}</strong></p>
                            <p class="manufacturer">by ${product.manufacturer_name}</p>


                            <!-- Form for adding product to cart -->
							<form action="${pageContext.request.contextPath}/AddToCartController" method="post">
							    <input type="hidden" name="user_id" value="${user.user_id}" />
							    <input type="hidden" name="product_id" value="${product.product_id}" />
							    
							    <label>
							        Quantity:
							        <input type="number" name="quantity" value="1" min="1" max="10" class="quantity-input" />
							    </label>
							    
    							<button type="submit" class="add-to-cart-btn">Add to Cart</button>
							</form>	
						</div>

                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>

    <footer class="footer">
        <p>&copy; 2025 DMart</p>
    </footer>

</body>
</html>
