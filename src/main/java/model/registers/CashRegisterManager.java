package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.products.ProductList;
import model.users.User;
import util.Money;

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

	public List<CashRegister> getRegisters() {
		return this.registers;
	}


	
	public void addInQueueUsertoACashRegister(CashRegister aCashRegister, InQueueUser anInQueueUser){
		aCashRegister.add(anInQueueUser);
		aCashRegister.next();
	}

	public void addInQueueUser(InQueueUser anInQueueUser) {
		this.addInQueueUsertoACashRegister(this.getNextCashRegisterFor(anInQueueUser.getProductList()), anInQueueUser);
	}

	public CashRegister getNextCashRegisterFor(ProductList aProductList){
		CashRegister cashRegister = null;
		for(CashRegister cr : this.getRegisters()){
			if(cashRegister == null && cr.accepts(aProductList)){
				cashRegister = cr;
			}else{
				if(cr.accepts(aProductList) && cashRegister.getWaitingTime().isLongerThan(cr.getWaitingTime())){
					cashRegister = cr;
				}				
			}
		}
		return cashRegister;
	}

	public void changeFilterFor(int index, Filter f) {
		this.getRegisters().get(index).useFilter(f);
	}
	/////////////////////////////////////////////////////////////
	
	public void closeCashRegister(int index){
		this.changeFilterFor(index, new CloseFilter());
	}

	public void openCashRegister(int index){
		this.changeFilterFor(index, new OpenFilter());
	}
	
	public void totalCostFilter(int index, Money money){
		this.changeFilterFor(index, new TotalCostFilter(money));
	}
	
	public void addCashRegister(CashRegister cr) {
		this.registers.add(cr);
	}
	
	public void removeCashRegister(CashRegister cr){
		this.registers.remove(cr);
	}
	
	public void addUserWithProductList(User user, ProductList pl){
		this.addInQueueUser(new InQueueUser(pl, user));
	}

	public Duration getWaitingTime(ProductList aProductList) {
		return this.getNextCashRegisterFor(aProductList).getWaitingTime();
	}

}
