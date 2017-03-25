package model;

import java.util.HashMap;

public class ProductList {

	
	String name;
	//ArrayList<Umbral> umbrals;
	
	String totalAmount;
	HashMap<Product , String> allProducts;
	
	
	
	
	public void addProduct(Product product , String howMany) {
		if ( ! allProducts.containsKey(product)) {
			allProducts.put(product, howMany);
			this.updateAmount( this.calculateAmount(product.getPrice() , howMany) );
		}	
	}
	
	public void updateAmount(String newAmount) {
		this.totalAmount += newAmount;
	}
	
	public String calculateAmount(String unitPrice , String quantity) {
		return "unitPrice * quantity";
	}
	
}
