package model.products;

import org.joda.time.Duration;

import util.Category;
import util.Entity;
import util.Money;

public class Product extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7281685467288672502L;
	private String name;
	private String brand;
	private Integer stock;
	private Money price;
	private Category category;
	private Duration processingTime;
	private String imageUrl;
	
	private java.time.Duration persistentProcessingTime;
	
	
	
	public Product (String newName , String newBrand , Integer newStock , Money newPrice , Category newCategory) {
		this.setName(newName);
		this.setBrand(newBrand);
		this.setStock(newStock);
		this.setPrice(newPrice);
		this.setCategory(newCategory);
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
		return 
			this.getName().equals(someProduct.getName()) && 
			this.getBrand().equals(someProduct.getBrand());
	}
	
	
	

	public Product(){
		setProcessingTime(new Duration(2L));
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public Integer getStock() {
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
	
	public void setStock(Integer newStock) {
		this.stock = newStock;
	}
	
	public void setPrice(Money newPrice) {
		this.price = newPrice;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Duration getProcessingTime() {
		return this.processingTime;
	}
	public void setProcessingTime(Duration newDuration){
		this.processingTime = newDuration;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getImageUrl(){
		return this.imageUrl;
	}
}


