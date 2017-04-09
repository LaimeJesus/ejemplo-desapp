package model.registers;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

import model.ProductList;
import model.registers.CashRegister;
import model.registers.CashRegisterManager;

public class CashRegisterManagerTest {
	
	
	@Test
	public void testCreateACashRegisterManagerWith10CashRegistersHas10CashRegisters(){
		CashRegisterManager sut = new CashRegisterManager(10);
		
		Assert.assertEquals(10, sut.getRegisters().size());
	}
	
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
