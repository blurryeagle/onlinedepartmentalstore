<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CartStyles.css" />
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

<main class="cart-container">
    <h2>Shopping Cart</h2>

    <c:if test="${empty cartItems}">
        <p>Your cart is empty.</p>
    </c:if>

    <c:if test="${not empty cartItems}">
        <form action="${pageContext.request.contextPath}/OrderController" method="post">
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Image</th>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td><img class="cart-product-image" src="${item.product.product_image_path}" alt="Image"/></td>
                        <td>${item.product.name}</td>
                        <td>Rs. <fmt:formatNumber value="${item.product.price}" type="number" minFractionDigits="2"/></td>
                        <td>
                            <input type="text"
                                   value="${item.cart.quantity}"
                                   readonly
                                   placeholder="${item.cart.quantity}"
                                   class="quantity-display"
                                   style="width: 40px; text-align: center;"
                                   />
                            <c:if test="${item.cart.quantity > 10}">
                                <span style="color: red;">Max 10 allowed</span>
                            </c:if>
                        </td>
                        <td>Rs. <fmt:formatNumber value="${item.subtotal}" type="number" minFractionDigits="2"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/CartController?action=remove&productId=${item.product.id}" class="remove-btn">Cancel</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" style="text-align: right;"><strong>Grand Total:</strong></td>
                    <td colspan="2"><strong>Rs. <fmt:formatNumber value="${total}" type="number" minFractionDigits="2"/></strong></td>
                </tr>
                </tfoot>
            </table>

            <div class="cart-actions">
                <button type="submit" class="checkout-btn">Proceed to Checkout</button>
            </div>
        </form>
    </c:if>
</main>

<footer class="footer">
    <p>Need help? Visit our <a href="#">Support Center</a> or <a href="#">Contact Us</a>.</p>
</footer>

</body>
</html>
