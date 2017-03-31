package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

public class CashRegister {

	private List<ProductList> productLists;

	public CashRegister(){
		productLists = new ArrayList<ProductList>();
	}
	
	public Duration getWaitingTime() {
		Duration totalWaitingTime = new Duration(0L);
		for(ProductList pl : this.getProductLists()){
			totalWaitingTime= totalWaitingTime.plus(pl.getProcessingTime());
		}
		return totalWaitingTime;
	}
	
	public void add(ProductList aProductList) {
		this.getProductLists().add(aProductList);
	}

	private List<ProductList> getProductLists() {
		return this.productLists;
	}

	public void remove(ProductList aProductList) {
		this.getProductLists().remove(aProductList);
	}
}