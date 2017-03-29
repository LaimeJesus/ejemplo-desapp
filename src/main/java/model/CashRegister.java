package model;

import org.joda.time.Duration;

public class CashRegister {

	private Duration waitingTime;

	public CashRegister(){
		waitingTime = new Duration(0L);
	}
	
	public Duration getWaitingTime() {
		return this.waitingTime;
	}

}