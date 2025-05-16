package com.OnlineDepartmentalStore.model;

public class Manufacturer {
	 private int manufacturerId;
	 private String name;
	 
	 public Manufacturer() {
		// Default Constructor
	}
	 
	 public Manufacturer(int manufacturerId, String name) {
		 super();
		 this.manufacturerId = manufacturerId;
		 this.name = name;
	 }

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
}
