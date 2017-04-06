package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.ProductList;
import model.User;

public class CashRegisterManager {

	private List<CashRegister> registers;

	public CashRegisterManager(){
		this.registers = new ArrayList<CashRegister>();
	}
	
	public CashRegisterManager(Integer n){
		this.registers = new ArrayList<CashRegister>();
		for(int i=0; i<n; i++){
			CashRegister aCashRegister = new CashRegister();
			this.addCashRegister(aCashRegister);			
		}
	}
	
	public void addCashRegister(CashRegister cr) {
		this.registers.add(cr);
	}

	public List<CashRegister> getRegisters() {
		return this.registers;
	}

	public Duration getWaitingTime(ProductList pl) {
		return this.getNextCashRegisterFor(pl).getWaitingTime();
	}

	public void changeFilterFor(int index, Filter f) {
		this.getRegisters().get(index).useFilter(f);
	}

	public void newUserToQueue(ProductList aProductList, User user) {
		CashRegister aCashRegister = this.getNextCashRegisterFor(aProductList);
		InQueueUser newInQueueUser = new InQueueUser(aProductList, user);
		aCashRegister.add(newInQueueUser);
		aCashRegister.next();				
	}

	public CashRegister getNextCashRegisterFor(ProductList aProductList){
		CashRegister cashRegister = null;
		for(CashRegister cr : this.getRegisters()){
			if(cashRegister == null && cr.accepts(aProductList)){
				cashRegister = cr;
			}else{
				if(cr.accepts(aProductList) && cr.getWaitingTime().isLongerThan(cashRegister.getWaitingTime())){
					cr = cashRegister;
				}				
			}
		}
		return cashRegister;
	}
	
}
