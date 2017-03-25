package model;

import util.Money;

public class Product {
	
	String id;
	String name;
	String brand;
	String stock;
	Money price;
	
	public Product (String newName , String newBrand , String newStock , Money newPrice) {
		this.name = newName;
		this.brand = newBrand;
		this.stock = newStock;
		this.price = newPrice;
	}
	
	
	
	
	
	
	//////////GETTERS Y SETTERS
	public String getName() {
		return this.name;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public String getStock() {
		return this.stock;
	}
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setBrand(String newBrand) {
		this.brand = newBrand;
	}
	
	public void setStock(String newStock) {
		this.stock = newStock;
	}
	
	public void setPrice(Money newPrice) {
		this.price = newPrice;
	}
	
	
}


