package model;

public class Product {
	
	String id;
	String name;
	String brand;
	String stock;
	String price;
	
	public Product (String newName , String newBrand , String newStock , String newPrice) {
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
	
	public String getPrice() {
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
	
	public void setPrice(String newPrice) {
		this.price = newPrice;
	}
	
	
}


