package model.registers;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.products.ProductList;
import model.registers.CashRegister;
import model.registers.CloseFilter;
import model.registers.InQueueUser;
import model.registers.TotalCostFilter;
import model.registers.TotalProductsFilter;
import util.Money;

public class CashRegisterTest {
	
	@Test
	public void testANewCashRegisterIsEmpty(){
		CashRegister aCashRegister = new CashRegister();
		Assert.assertTrue(aCashRegister.isEmpty());
		Assert.assertEquals(0, aCashRegister.size());
		aCashRegister.stop();
	}
	
	@Test
	public void testANewCashRegisterHasWaitingTimeOfZeroSeconds(){
		CashRegister newCashRegister = new CashRegister();
		
		Duration expected = new Duration(0L);
		Duration actual = newCashRegister.getWaitingTime();
		
		Assert.assertEquals(expected, actual);
		newCashRegister.stop();
	}
	
	@Test
	public void testAddingAnUserWithAProductListWhichItsDurationIs5secondsThenIncreaseTheWaitingTimeFor5Sec(){
		CashRegister emptyCashRegister = new CashRegister();
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(5L));
		
		emptyCashRegister.add(newInQueueUserMock);		
		
		Duration expected = new Duration(5L);
		Duration actual = emptyCashRegister.getWaitingTime();
		
		Assert.assertEquals(expected, actual);
		emptyCashRegister.stop();
	}
	
	@Test
	public void testRemovingAnUserInTheQueueWithAProductListWhereItsDurationTimeIs5secondsThenDecreaseTheWaitingTimeFor5seconds(){
		CashRegister aCashRegister = new CashRegister();
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(5L));

		aCashRegister.add(newInQueueUserMock);
		Duration afterAdded = aCashRegister.getWaitingTime();
		aCashRegister.remove(newInQueueUserMock);
		Duration afterRemoved = aCashRegister.getWaitingTime();
		
		Assert.assertTrue(afterAdded.isLongerThan(afterRemoved));
		Assert.assertEquals(new Duration(0L), afterRemoved);
		aCashRegister.stop();
	}
	
	//this test runs in 0,001sec
	@Test
	public void testACashRegisterCanTakeTheNextClientInTheQueue(){
		CashRegister aCashRegister = new CashRegister();
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(1L));

		aCashRegister.add(newInQueueUserMock);
		
		Mockito.doNothing().when(newInQueueUserMock).newPurchase();
		
		aCashRegister.next();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(aCashRegister.isEmpty());
		aCashRegister.stop();
	}
	
	@Test
	public void testACashRegisterOnlyAcceptsProductListsWithTotalCostLesserThan50(){
		CashRegister aCashRegister = new CashRegister();
		TotalCostFilter costLesserThan50Filter = new TotalCostFilter(new Money(50,0));
		aCashRegister.useFilter(costLesserThan50Filter);
		
		ProductList aProductListWithTotalCostEquals100Mock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListWithTotalCostEquals100Mock.getTotalAmount()).thenReturn(new Money(100,0));
		
		ProductList aProductListWithTotalCostEquals45Mock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListWithTotalCostEquals45Mock.getTotalAmount()).thenReturn(new Money(45,0));

		Assert.assertFalse(aCashRegister.accepts(aProductListWithTotalCostEquals100Mock));
		Assert.assertTrue(aCashRegister.accepts(aProductListWithTotalCostEquals45Mock));
		aCashRegister.stop();
	}
	
	@Test
	public void testACashRegisterOnlyAcceptsProductListsWith10ProductsOrLess(){
		CashRegister aCashRegister = new CashRegister();
		TotalProductsFilter lesserThan10productsFilter = new TotalProductsFilter(10);
		aCashRegister.useFilter(lesserThan10productsFilter);
		
		ProductList aProductListWith15ProductsMock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListWith15ProductsMock.getQuantityOfProducts()).thenReturn(15);
		
		ProductList aProductListWith5ProductsMock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListWith5ProductsMock.getQuantityOfProducts()).thenReturn(5);

		Assert.assertFalse(aCashRegister.accepts(aProductListWith15ProductsMock));
		Assert.assertTrue(aCashRegister.accepts(aProductListWith5ProductsMock));
		aCashRegister.stop();
	}
	
	@Test
	public void testAClosedCashRegisterCannotAcceptAnyUser(){
		CashRegister aCashRegister = new CashRegister();
		CloseFilter closeFilter = new CloseFilter();
		aCashRegister.useFilter(closeFilter);
		
		ProductList aProductList = Mockito.mock(ProductList.class);
		
		Assert.assertFalse(aCashRegister.accepts(aProductList));
		aCashRegister.stop();
	}
	
	@Test
	public void testAOpenCashRegisterCanAcceptAnyUser(){
		CashRegister aCashRegister = new CashRegister();
		OpenFilter openFilter = new OpenFilter();
		aCashRegister.useFilter(openFilter);
		
		ProductList aProductList = Mockito.mock(ProductList.class);
		
		Assert.assertTrue(aCashRegister.accepts(aProductList));
		aCashRegister.stop();
	}
	
}
