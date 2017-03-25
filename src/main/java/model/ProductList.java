package model;

import java.util.HashMap;

import util.Money;

public class ProductList {

	
	String name;
	//ArrayList<Umbral> umbrals;
	
	Money totalAmount;
	HashMap<Product , Integer> allProducts;
	
	
	
	
	public void addProduct(Product product , Integer howMany) {
		if ( ! allProducts.containsKey(product)) {
			allProducts.put(product, howMany);
			this.updateAmount( this.calculateAmount(product.getPrice() , howMany) );
		}	
	}
	
	public void updateAmount(Money newAmount) {
		totalAmount.plus(newAmount);
	}
	
	public Money calculateAmount(Money unitPrice , Integer quantity) {
		unitPrice.times(quantity);
		return unitPrice;
	}
	
}
