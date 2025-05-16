package com.OnlineDepartmentalStore.model;

import java.sql.Date;

public class Order {
	private int order_id;
    private int user_id;
    private Date order_date;
    private String status; // 'pending', 'completed', or 'cancelled'
    private double total_price;
    
	public Order(int order_id, int user_id, Date order_date, String status, double total_price) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.order_date = order_date;
		this.status = status;
		this.total_price = total_price;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
    
}
