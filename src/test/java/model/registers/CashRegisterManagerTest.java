package model.registers;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.CanNotGetCashRegister;
import exceptions.RegisterDoesNotExistException;
import model.products.ProductList;
import model.registers.CashRegister;
import model.registers.CashRegisterManager;

public class CashRegisterManagerTest {
	
	@Test
	public void testGettingTheCashRegisterWithLesserWaitingTime() throws CanNotGetCashRegister{
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegister1Mock = Mockito.mock(CashRegister.class);
		CashRegister aCashRegister2Mock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aCashRegister1Mock.getWaitingTime()).thenReturn(new Duration(5L));
		Mockito.when(aCashRegister1Mock.accepts(aProductListMock)).thenReturn(true);

		Mockito.when(aCashRegister2Mock.getWaitingTime()).thenReturn(new Duration(4L));
		Mockito.when(aCashRegister2Mock.accepts(aProductListMock)).thenReturn(true);		
		
		sut.addCashRegister(aCashRegister1Mock);
		sut.addCashRegister(aCashRegister2Mock);
		
		Assert.assertEquals(aCashRegister2Mock, sut.getNextCashRegisterFor(aProductListMock));		
		
		sut.stop();
	}
	
	@Test(expected=CanNotGetCashRegister.class)
	public void testGettingTheCashRegisterWithLesserTimeWhenAllMyCashRegisterAreClosedThenReturnsNull() throws CanNotGetCashRegister{
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegister1Mock = Mockito.mock(CashRegister.class);
		CashRegister aCashRegister2Mock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aCashRegister1Mock.accepts(aProductListMock)).thenReturn(false);
		Mockito.when(aCashRegister2Mock.accepts(aProductListMock)).thenReturn(false);		
		
		sut.addCashRegister(aCashRegister1Mock);
		sut.addCashRegister(aCashRegister2Mock);
		
		sut.getNextCashRegisterFor(aProductListMock);
		sut.stop();
	}
	
	@Test
	public void testGettingTheWaitingTimeWithTwoCashRegisterWith5secondsAndTheOtherWith8SecondsReturns4Seconds() throws CanNotGetCashRegister{
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegister1Mock = Mockito.mock(CashRegister.class);
		CashRegister aCashRegister2Mock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aCashRegister1Mock.getWaitingTime()).thenReturn(new Duration(5L));
		Mockito.when(aCashRegister1Mock.accepts(aProductListMock)).thenReturn(true);

		Mockito.when(aCashRegister2Mock.getWaitingTime()).thenReturn(new Duration(4L));
		Mockito.when(aCashRegister2Mock.accepts(aProductListMock)).thenReturn(true);		
		
		sut.addCashRegister(aCashRegister1Mock);
		sut.addCashRegister(aCashRegister2Mock);
		
		Assert.assertEquals(new Duration(4L), sut.getWaitingTime(aProductListMock));	
		sut.stop();
	}
	
	@Test
	public void testAnUserWithAProductListIsAddedToTheCashRegisterWithTheLesserTime() throws CanNotGetCashRegister{
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegisterMock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		InQueueUser anInQueueUserMock = Mockito.mock(InQueueUser.class);
		
		Mockito.when(anInQueueUserMock.getProductList()).thenReturn(aProductListMock);
		Mockito.when(aCashRegisterMock.getWaitingTime()).thenReturn(new Duration(5L));
		Mockito.when(aCashRegisterMock.accepts(aProductListMock)).thenReturn(true);
		
		sut.addCashRegister(aCashRegisterMock);
		sut.addInQueueUser(anInQueueUserMock);
		
		Mockito.verify(aCashRegisterMock).next();
		Mockito.verify(anInQueueUserMock).getProductList();
		sut.stop();
	}
	
	@Test
	public void testChangeACashRegisterToClose() throws RegisterDoesNotExistException{
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegisterMock = Mockito.mock(CashRegister.class);
		
		CloseFilter newCloseFilter = new CloseFilter(); 
		
		sut.addCashRegister(aCashRegisterMock);
		sut.changeFilterFor(0, newCloseFilter);
		
		Mockito.verify(aCashRegisterMock).useFilter(newCloseFilter);
		sut.stop();
	}
	
}
