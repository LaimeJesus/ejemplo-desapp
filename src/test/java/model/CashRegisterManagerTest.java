package model;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

import model.registers.CashRegister;
import model.registers.CashRegisterManager;

public class CashRegisterManagerTest {
	
	@Test
	public void testCanGetTheLesserTimeOfMyCashRegisters(){
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister cr = new CashRegister();
		
		sut.addCashRegister(cr);
		
		ProductList pl = new ProductList();
				
		CashRegister newCR = sut.getNextCashRegisterFor(pl);
		
		Duration expected = newCR.getWaitingTime();
		Duration actual = new Duration(0L);
		
		Assert.assertEquals(expected, actual);
	}

}
