package model.alerts;

import model.products.Product;
import model.products.ProductList;
import util.Entity;

public abstract class Alert extends Entity {

	private Boolean isOn;

	public Alert(Boolean isOn){
		this.setIsOn(isOn);
	}
	
	private void setIsOn(Boolean isOn) {
		this.isOn = isOn;
	}

	public Boolean canDisplayAlert(ProductList aProductList, Product aProduct, Integer aQuantity){
		return this.isOn() && this.satisfy(aProductList, aProduct, aQuantity);
	}
	
	public Boolean canDisplayAlert(ProductList aProductList){
		return this.isOn() && this.satisfy(aProductList);
	}
	
	public abstract Boolean satisfy(ProductList aProductList);

	public abstract Boolean satisfy(ProductList aProductList, Product aProduct, Integer aQuantity);

	public Boolean isOn(){
		return this.isOn;
	}
	
	public void activate(){
		this.setIsOn(true);
	}
	
	public void shutdown(){
		this.setIsOn(false);
	}
	
}
