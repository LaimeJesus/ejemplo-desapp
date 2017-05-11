package model.products;

import org.joda.time.Duration;

import util.Entity;
import util.Money;

public class SelectedProduct extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8915491491245495928L;
	private Product product;
	private Integer quantity;
	
	
	public SelectedProduct(Product newProduct , Integer newQuantity) {
		this.setProduct(newProduct);
		this.setQuantity(newQuantity);
	}
	
	
	public SelectedProduct () {
		
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


	public Money getFinalPrice() {
		return this.getProduct().getPrice().times(this.getQuantity());
	}
	
}
