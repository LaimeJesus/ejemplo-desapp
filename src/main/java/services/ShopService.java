package services;

import org.joda.time.Duration;

import model.ProductList;
import model.User;
import model.registers.CashRegisterManager;
import model.registers.Filter;
import model.registers.InQueueUser;

public class ShopService {
	
	private CashRegisterManager cashRegisterManager;

	public ShopService(int cashRegisters){
		this.cashRegisterManager = new CashRegisterManager(cashRegisters);		
	}
	
	public void pay(ProductList aProductList, User anUser){
		InQueueUser newInQueueUser = new InQueueUser(aProductList, anUser);
		this.cashRegisterManager.newUserToQueue(newInQueueUser);
	}
	
	public Duration getWaitingTime(ProductList pl){
		return this.cashRegisterManager.getWaitingTime(pl);
	}
	
	public void delivery(ProductList pl, User anUser){
		//deliveryService.delivery(pl, anUser)
	}
	
	public void changeFilter(int index, Filter f){
		this.cashRegisterManager.changeFilterFor(index, f);
	}
	
	
}
