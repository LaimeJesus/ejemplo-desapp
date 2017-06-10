package model.alerts;

import model.products.Product;
import model.products.ProductList;
import util.Money;

public class PriceAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2705995157525891027L;
	private Money limit;
	
	public PriceAlert(Money aMoney, Boolean isOn) {
		super(isOn);
		this.setLimit(aMoney);
	}

	public PriceAlert() {
	}

	private void setLimit(Money aLimit) {
		this.limit = aLimit;
	}
	private Money getLimit() {
		return this.limit;
	}

	@Override
	public Boolean satisfy(ProductList aProductList){
		return aProductList.getTotalAmount().greaterThan(this.getLimit());
	}

	@Override
	public Boolean satisfy(ProductList aProductList, Product aProduct, Integer aQuantity) {
		return aProductList.getTotalAmount().add(aProduct.getPrice().times(aQuantity)).greaterThan(this.getLimit());
	}

	@Override
	public String toString() {
		return "Alert for " + this.getLimit().toString() + " limit price";
	}
}