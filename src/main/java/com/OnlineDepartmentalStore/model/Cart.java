package com.OnlineDepartmentalStore.model;

public class Cart {
	private int cart_id;
    private int user_id;
    private int product_id;
    private int quantity;
    
	public Cart(int user_id, int product_id, int quantity) {
		super();
		this.user_id = user_id;
		this.product_id = product_id;
		this.quantity = quantity;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
