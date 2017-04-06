package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.ProductList;

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

	public CashRegister getLesserTime(ProductList pl) {
		CashRegister cr = this.registers.get(0);
		for(CashRegister cashRegister : this.registers){
			if(cr.getWaitingTime().isLongerThan(cashRegister.getWaitingTime())){
				cr = cashRegister;
			}
		}
		return cr;
	}

	public List<CashRegister> getRegisters() {
		return this.registers;
	}

	public void addProductList(ProductList pl) {
		CashRegister aCashRegister = this.getLesserTime(pl);
		aCashRegister.addUsingFilter(pl);
		aCashRegister.next();
		
	}

	public Duration getWaitingTime(ProductList pl) {
		return this.getLesserTime(pl).getWaitingTime();
	}

	public void changeFilterFor(int index, Filter f) {
		this.getRegisters().get(index).useFilter(f);
	}

}
