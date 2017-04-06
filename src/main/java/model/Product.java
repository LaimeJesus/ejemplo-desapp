package model;

import util.Category;
import util.Money;

public class Product {
	
	private String name;
	private String brand;
	private String stock;
	private Money price;
	private Category category;
	
	public Product (String newName , String newBrand , String newStock , Money newPrice , Category newCategory) {
		this.setName(newName);
		this.setBrand(newBrand);
		this.setStock(newStock);
		this.setPrice(newPrice);
		this.setCategory(newCategory);
	}
	
	public Product(){
		
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
	
	
	@Override
	public boolean equals(Object anyObject) {
		
		if (this.isMyType(anyObject)) {
			Product newProduct = (Product) anyObject;
			return this.totalEquals(newProduct) ;
		}
		return false;
		
	}
	
	private boolean isMyType(Object anyObject) {
		return anyObject != null && anyObject instanceof Product;
	}
	
	private boolean totalEquals(Product someProduct) {
		return this.getName().equals(someProduct.getName()) && this.getBrand().equals(someProduct.getBrand());
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}


