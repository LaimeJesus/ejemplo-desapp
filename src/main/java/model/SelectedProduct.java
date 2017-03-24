package model;

public class SelectedProduct {
	
	Product product;
	Integer quantity;
	
	
	public SelectedProduct(Product newProduct , Integer newQuantity) {
		this.product = newProduct;
		this.quantity = newQuantity;
	}
	
}
