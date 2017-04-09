package model.registers;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.ProductList;
import model.registers.CashRegister;
import model.registers.CloseFilter;
import model.registers.InQueueUser;
import model.registers.TotalCostFilter;
import model.registers.TotalProductsFilter;
import util.Money;

public class CashRegisterTest {
	
	@Test
	public void testANewCashRegisterIsEmpty(){
		Assert.assertTrue(new CashRegister().isEmpty());
	}
	
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
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(5L));
		
		emptyCashRegister.add(newInQueueUserMock);		
		
		Duration expected = new Duration(5L);
		Duration actual = emptyCashRegister.getWaitingTime();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testRemoveAProductListDesincrementTheWaitingTime(){
		CashRegister emptyCashRegister = new CashRegister();
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(5L));

		emptyCashRegister.add(newInQueueUserMock);
		Duration afterAdded = emptyCashRegister.getWaitingTime();
		emptyCashRegister.remove(newInQueueUserMock);
		Duration afterRemoved = emptyCashRegister.getWaitingTime();
		
		Assert.assertTrue(afterAdded.isLongerThan(afterRemoved));
		Assert.assertEquals(new Duration(0L), afterRemoved);
		
	}
	
	//this test runs in 0,001sec
	@Test
	public void testACashRegisterCanTakeTheNextClientInTheQueue(){
		CashRegister aCashRegister = new CashRegister();
		
		InQueueUser newInQueueUserMock = Mockito.mock(InQueueUser.class);
		Mockito.when(newInQueueUserMock.getProcessingTime()).thenReturn(new Duration(1L));

		aCashRegister.add(newInQueueUserMock);
		
		aCashRegister.next();
		
		Assert.assertTrue(aCashRegister.isEmpty());
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

		boolean notAccepted = aCashRegister.accepts(aProductListWithTotalCostEquals100Mock);
		boolean accepted = aCashRegister.accepts(aProductListWithTotalCostEquals45Mock);
		
		Assert.assertFalse(notAccepted);
		Assert.assertTrue(accepted);
		
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

		boolean notAccepted = aCashRegister.accepts(aProductListWith15ProductsMock);
		boolean accepted = aCashRegister.accepts(aProductListWith5ProductsMock);
		
		Assert.assertFalse(notAccepted);
		Assert.assertTrue(accepted);
		
	}
	
	@Test
	public void testAClosedCashRegisterCannotAcceptProductLists(){
		CashRegister aCashRegister = new CashRegister();
		CloseFilter closeFilter = new CloseFilter();
		aCashRegister.useFilter(closeFilter);
		
		ProductList aProductList = Mockito.mock(ProductList.class);
		
		boolean notAccepted = aCashRegister.accepts(aProductList);
		
		Assert.assertFalse(notAccepted);
	}
	
}
