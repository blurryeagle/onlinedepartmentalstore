package com.OnlineDepartmentalStore.model;

public class Product {
	private int product_id;
    private String name;
    private double price;
    private int stock_quantity;
    private int category_id;
    private int manufacturer_id;
    private String manufacturer_name;
	private String product_image_path;

	public Product(int product_id, String name, double price, int stock_quantity, int category_id, String manufacturer_name,
			String product_image_path) {
		super();
		this.product_id = product_id;
		this.name = name;
		this.price = price;
		this.stock_quantity = stock_quantity;
		this.category_id = category_id;
		this.setManufacturer_name(manufacturer_name);
		this.product_image_path = product_image_path;
	}
	
	public Product(String name, double price, int stock_quantity, int category_id, int manufacturer_id,
			String product_image_path) {
		super();
		this.name = name;
		this.price = price;
		this.stock_quantity = stock_quantity;
		this.category_id = category_id;
		this.setManufacturer_id(manufacturer_id);
		this.product_image_path = product_image_path;
	}


	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getProduct_image_path() {
		return product_image_path;
	}

	public void setProduct_image_path(String product_image_path) {
		this.product_image_path = product_image_path;
	}

	public int getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(int manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	public String getManufacturer_name() {
		return manufacturer_name;
	}

	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}

    
}
