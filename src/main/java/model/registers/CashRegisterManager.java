package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.products.ProductList;

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

	public Duration getWaitingTime(ProductList aProductList) {
		return this.getNextCashRegisterFor(aProductList).getWaitingTime();
	}

	public void changeFilterFor(int index, Filter f) {
		this.getRegisters().get(index).useFilter(f);
	}
	
	public void addInQueueUsertoACashRegister(CashRegister aCashRegister, InQueueUser anInQueueUser){
		aCashRegister.add(anInQueueUser);
		aCashRegister.next();
	}

	public void newUserToQueue(InQueueUser anInQueueUser) {
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
		//si cash register es null entonces aca deberia lanzar una excepcion,
		//ya que no hay cajas disponibles
		return cashRegister;
	}
	
}
