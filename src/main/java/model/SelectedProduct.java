package model;

import org.joda.time.Duration;

public class SelectedProduct {
	
	private Product product;
	private Integer quantity;
	
	
	public SelectedProduct(Product newProduct , Integer newQuantity) {
		this.setProduct(newProduct);
		this.setQuantity(newQuantity);
	}
	
	
	public Product getProduct() {
		return this.product;
	}
	
	public void setProduct(Product newProduct) {
		this.product = newProduct;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(Integer newQuantity) {
		this.quantity = newQuantity;
	}

	public Duration getProcessingTime(){
		return this.getProduct().getProcessingTime().multipliedBy(this.getQuantity());
	}
	
}
