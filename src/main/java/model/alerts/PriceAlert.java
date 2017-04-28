package model.alerts;

import model.products.Product;
import model.products.ProductList;
import util.Monetizable;
import util.Money;

public class PriceAlert extends Alert implements Monetizable{

	private Money limit;

	public PriceAlert(Money aMoney, Boolean isOn) {
		super(isOn);
		this.setLimit(aMoney);
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
	public Money getMonetizableElement() {
		return this.limit;
	}

	@Override
	public void setMonetizableElement(Money newMoney) {
		this.limit = newMoney;
	}
}