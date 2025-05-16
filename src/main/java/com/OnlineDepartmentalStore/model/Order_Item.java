package com.OnlineDepartmentalStore.model;

public class Order_Item {
	private int order_item_id;
    private int order_id;
    private int product_id;
    private int quantity;
    private double price_at_purchase;
    
	public Order_Item(int order_item_id, int order_id, int product_id, int quantity, double price_at_purchase) {
		super();
		this.order_item_id = order_item_id;
		this.order_id = order_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price_at_purchase = price_at_purchase;
	}

	public int getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(int order_item_id) {
		this.order_item_id = order_item_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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

	public double getPrice_at_purchase() {
		return price_at_purchase;
	}

	public void setPrice_at_purchase(double price_at_purchase) {
		this.price_at_purchase = price_at_purchase;
	}
    
	
    
}
