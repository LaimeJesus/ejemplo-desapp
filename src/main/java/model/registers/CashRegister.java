package model.registers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.joda.time.Duration;
import model.products.ProductList;

public class CashRegister {

	private Duration waitingTime;
	private List<InQueueUser> queue;
	public Filter filter;
	private boolean isRunning;
	public ExecutorService executor;
	public Integer id;

	public CashRegister(){
		initialize();
	}
	
	public CashRegister(int id) {
		this.id = id;
		initialize();
	}
	
	public void initialize(){
		waitingTime = new Duration(0L);
		filter = new OpenFilter();
		queue = new ArrayList<InQueueUser>();
		isRunning = false;
		executor = Executors.newSingleThreadExecutor();
	}

	public Duration getWaitingTime() {
		return waitingTime;
	}
	
	private List<InQueueUser> getQueue() {
		return this.queue;
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	public void next() {
		CashRegister self = this;
		if(this.canProcess()){
			executor.execute(new Runnable(){
				@Override
				public void run(){
					try {
						InQueueUser user = self.queue.get(0);
						self.isRunning = true;
						//1000 milliseconds is one second.
					    Thread.sleep(user.getProcessingTime().getMillis());
						self.remove(user);
					    self.isRunning = false;
					    self.next();
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					    self.isRunning = false;
					    executor.shutdown();
					}
				}					
				});
		}
	}

	private void setWaitingTime(Duration waitingTime) {
		this.waitingTime = waitingTime;
	}

	public void remove(InQueueUser user) {
		this.getQueue().remove(user);
		this.processTime(user.getProcessingTime());
	}
	
	public Boolean canProcess(){
		return !this.isEmpty() && !this.isRunning;
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

	public void add(InQueueUser newUser) {
		this.getQueue().add(newUser);
		this.updateTime(newUser.getProcessingTime());
	}

	private void updateTime(Duration duration) {
		this.setWaitingTime(getWaitingTime().plus(duration));
	}

	private void processTime(Duration duration) {
		this.setWaitingTime(getWaitingTime().minus(duration));
	}
	
	public void active(){
		executor = Executors.newSingleThreadExecutor();
	}
	
	public void stop(){
		executor.shutdown();
	}
}