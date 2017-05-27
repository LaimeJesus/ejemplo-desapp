package model.registers;

import org.joda.time.Duration;
import model.products.ProductList;
import model.users.User;

public class InQueueUser {

	private ProductList productList;
	private User user;

	public InQueueUser(ProductList aProductList, User anUser) {
		this.setProductList(aProductList);
		this.setUser(anUser);
	}

	public Duration getProcessingTime() {
		return this.getProductList().getProcessingTime();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductList getProductList() {
		return productList;
	}

	public void setProductList(ProductList productList) {
		this.productList = productList;
	}

	public void newPurchase() {
		this.getUser().newPurchase(new PurchaseRecord(this.getProductList()));
	}
	
}
