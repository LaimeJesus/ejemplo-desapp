package model;

import java.util.ArrayList;
import java.util.List;

public class CashRegisterManager {

	private List<CashRegister> registers;

	public CashRegisterManager(){
		registers = new ArrayList<CashRegister>();
	}
	
	public void addCashRegister(CashRegister cr) {
		registers.add(cr);
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

}
