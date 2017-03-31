package model;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CashRegisterTest {

	@Test
	public void testANewCashRegisterHasNoWaitingTime(){
		CashRegister newCashRegister = new CashRegister();
		
		Duration expected = new Duration(0L);
		Duration actual = newCashRegister.getWaitingTime();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testAddingAProductListIncrementTheWaitingTime(){
		CashRegister emptyCashRegister = new CashRegister();
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListMock.getProcessingTime()).thenReturn(new Duration(5L));
		
		emptyCashRegister.add(aProductListMock);		
		
		Duration expected = new Duration(5L);
		Duration actual = emptyCashRegister.getWaitingTime();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testRemoveAProductListDesincrementTheWaitingTime(){
		CashRegister emptyCashRegister = new CashRegister();
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		emptyCashRegister.add(aProductListMock);
		
		Mockito.when(aProductListMock.getProcessingTime()).thenReturn(new Duration(5L));
		
		Duration afterAdded = emptyCashRegister.getWaitingTime();
		
		emptyCashRegister.remove(aProductListMock);
		
		Duration afterRemoved = emptyCashRegister.getWaitingTime();
		
		Assert.assertNotEquals(afterAdded, afterRemoved);
		Assert.assertEquals(new Duration(0L), afterRemoved);
		
	}
}
