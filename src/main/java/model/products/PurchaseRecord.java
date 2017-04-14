package model.products;

import org.joda.time.DateTime;

public class PurchaseRecord {

	private DateTime purchasingDate;
	private ProductList productList;

	public PurchaseRecord(ProductList aProductList) {
		this.setPurchasingDate(DateTime.now());
		this.setProductList(aProductList);
	}

	public DateTime getPurchasingDate() {
		return this.purchasingDate;
	}

	public void setPurchasingDate(DateTime purchasingDate) {
		this.purchasingDate = purchasingDate;
	}

	public ProductList getProductList() {
		return this.productList;
	}

	public void setProductList(ProductList productList) {
		this.productList = productList;
	}
	
}
