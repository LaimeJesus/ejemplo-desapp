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
	
	public String getBrand() {
		return this.brand;
	}
	
	
}


