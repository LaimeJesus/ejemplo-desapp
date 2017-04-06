package model;

import org.joda.time.Duration;

public class ShopService {
	
	private CashRegisterManager cashRegisterManager;

	public ShopService(int cashRegisters){
		this.cashRegisterManager = new CashRegisterManager(cashRegisters);		
	}
	
	public void pay(ProductList pl){
		this.cashRegisterManager.addProductList(pl);
	}
	
	public Duration getWaitingTime(ProductList pl){
		return this.cashRegisterManager.getWaitingTime(pl);
	}
	
	public void delivery(ProductList pl){
		//deliveryService.delivery(pl)
	}
	
	public void changeFilter(int index, Filter f){
		this.cashRegisterManager.changeFilterFor(index, f);
	}
	
	
}
