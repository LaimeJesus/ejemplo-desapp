package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.ProductList;

public class CashRegister {

	private List<ProductList> productLists;
	private Filter filter;

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

	public boolean isEmpty() {
		return this.productLists.isEmpty();
	}

	public void next() {
		if(!this.isEmpty()){
			ProductList nextProductList = this.productLists.get(0);
			this.remove(nextProductList);
			this.process(nextProductList);
		}
	}

	private void process(ProductList nextProductList) {
		try {
			//1000 milliseconds is one second.
		    Thread.sleep(nextProductList.getProcessingTime().getMillis());
			//Thread.sleep(5000);
		    //updateStock(nextProductList)
		    //addToHistory(nextProductList)
		    this.next();
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	public void useFilter(Filter filter){
		this.filter = filter;		
	}

	public void addUsingFilter(ProductList aProductList) {
		if(this.filter.accepts(aProductList)){
			this.add(aProductList);
		}
		
	}

	public int size() {
		return this.getProductLists().size();
	}
}