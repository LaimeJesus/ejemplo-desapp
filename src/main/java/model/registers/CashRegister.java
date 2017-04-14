package model.registers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import model.products.ProductList;

public class CashRegister {

	private List<InQueueUser> queue;
	private Filter filter;

	public CashRegister(){
		filter = new OpenFilter();
		queue = new ArrayList<InQueueUser>();
	}
	
	public Duration getWaitingTime() {
		Duration totalWaitingTime = new Duration(0L);
		for(InQueueUser user : this.getQueue()){
			totalWaitingTime = totalWaitingTime.plus(user.getProcessingTime());
		}
		return totalWaitingTime;
	}
	
	private List<InQueueUser> getQueue() {
		return this.queue;
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	public void next() {
		if(!this.isEmpty()){
			InQueueUser nextUser = this.queue.get(0);
			this.remove(nextUser);
			this.process(nextUser);
		}
	}

	public void remove(InQueueUser user) {
		this.getQueue().remove(user);
		
	}

	private void process(InQueueUser user) {
		try {
			//1000 milliseconds is one second.
		    Thread.sleep(user.getProcessingTime().getMillis());
			//Thread.sleep(5000);
		    //updateStock(nextProductList)
		    user.newPurchase();
		    this.next();
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	public void useFilter(Filter filter){
		this.filter = filter;		
	}

	public boolean accepts(ProductList pl){
		return this.filter.accepts(pl);
	}

	public int size() {
		return this.getQueue().size();
	}

	public void add(InQueueUser newInQueueUser) {
		this.getQueue().add(newInQueueUser);
	}
}