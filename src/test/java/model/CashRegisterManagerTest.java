package model;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;

public class CashRegisterManagerTest {
	
	@Test
	public void testCanGetTheLesserTimeOfMyCashRegisters(){
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister cr = new CashRegister();
		
		sut.addCashRegister(cr);
		
		ProductList pl = new ProductList();
		
		//Product productMock = Mockito.mock(Product.class);
		
		//Mockito.when(productMock.getProccessingTime()).
		
		CashRegister newCR = sut.getLesserTime(pl);
		
		Duration expected = newCR.getWaitingTime();
		//Duration actual = pl.getTotalProcessingTime();
		Duration actual = new Duration(0L);
		
		Assert.assertEquals(expected, actual);
	}

}
