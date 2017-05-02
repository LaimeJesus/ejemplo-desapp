package model.registers;

import org.joda.time.DateTime;

import model.products.ProductList;
import util.Entity;

public class PurchaseRecord extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -616092953405797730L;
	private DateTime purchasingDate;
	private ProductList purchasingList;

	public PurchaseRecord(){
		this.setPurchasingDate(DateTime.now());
	}
	
	public PurchaseRecord(ProductList aProductList) {
		this.setPurchasingDate(DateTime.now());
		this.setPurchasingList(aProductList);
	}

	public DateTime getPurchasingDate() {
		return this.purchasingDate;
	}

	public void setPurchasingDate(DateTime purchasingDate) {
		this.purchasingDate = purchasingDate;
	}

	public ProductList getPurchasingList() {
		return this.purchasingList;
	}

	public void setPurchasingList(ProductList productList) {
		this.purchasingList = productList;
	}
	
}
