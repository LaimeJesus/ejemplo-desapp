package model;

public class SelectedProduct {
	
	Product product;
	Integer quantity;
	
	
	public SelectedProduct(Product newProduct , Integer newQuantity) {
		this.product = newProduct;
		this.quantity = newQuantity;
	}
	
	
	public Product getProduct() {
		return this.product;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(Integer newQuantity) {
		this.quantity = newQuantity;
	}
	
}
