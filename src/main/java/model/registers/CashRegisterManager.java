package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import exceptions.CanNotGetCashRegister;
import model.products.ProductList;
import model.users.User;
import rest.dtos.users.WaitingUser;
import util.Money;

public class CashRegisterManager {

	private List<CashRegister> registers;

	public CashRegisterManager(){
		this.registers = new ArrayList<CashRegister>();
	}
	
	public CashRegisterManager(Integer n){
		this.registers = new ArrayList<CashRegister>();
		for(int i=0; i<n; i++){
			CashRegister aCashRegister = new CashRegister(i);
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

	public void addInQueueUser(InQueueUser anInQueueUser) throws CanNotGetCashRegister {
		this.addInQueueUsertoACashRegister(this.getNextCashRegisterFor(anInQueueUser.getProductList()), anInQueueUser);
	}

	public CashRegister getNextCashRegisterFor(ProductList aProductList) throws CanNotGetCashRegister{
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
		if(cashRegister == null) throw new CanNotGetCashRegister(aProductList);
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
	
	public void addUserWithProductList(User user, ProductList pl) throws CanNotGetCashRegister{
		this.addInQueueUser(new InQueueUser(pl, user));
	}

	public Duration getWaitingTime(ProductList aProductList) throws CanNotGetCashRegister {
		return this.getNextCashRegisterFor(aProductList).getWaitingTime();
	}
	
	public void stop(){
		for(CashRegister cr : getRegisters()){
			cr.stop();
		} 
	}
	public void start(int n){
		if(getRegisters().stream().anyMatch(cr -> !cr.isEmpty())){
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		registers = new ArrayList<CashRegister>();
		for(int i=0;i<n;i++){
			registers.add(new CashRegister());
		}
	}

	public WaitingUser queueUserWithAProductlist(User user, ProductList pl) throws CanNotGetCashRegister {
		CashRegister cr = getNextCashRegisterFor(pl); 
		cr.add(new InQueueUser(pl, user));
		cr.next();
		return new WaitingUser(cr.id, pl.getId(), cr.getWaitingTime(), user.getId());
	}

}
