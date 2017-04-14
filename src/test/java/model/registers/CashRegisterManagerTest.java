package model.registers;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.products.ProductList;
import model.registers.CashRegister;
import model.registers.CashRegisterManager;

public class CashRegisterManagerTest {
	
	
	@Test
	public void testCreateACashRegisterManagerWith10CashRegistersHas10CashRegisters(){
		CashRegisterManager sut = new CashRegisterManager(10);
		
		Assert.assertEquals(10, sut.getRegisters().size());
	}
	
	@Test
	public void testGettingTheCashRegisterWithLesserWaitingTime(){
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
		
	}
	
	@Test
	public void testGettingTheCashRegisterWithLesserTimeWhenAllMyCashRegisterAreClosedThenReturnsNull(){
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegister1Mock = Mockito.mock(CashRegister.class);
		CashRegister aCashRegister2Mock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aCashRegister1Mock.accepts(aProductListMock)).thenReturn(false);
		Mockito.when(aCashRegister2Mock.accepts(aProductListMock)).thenReturn(false);		
		
		sut.addCashRegister(aCashRegister1Mock);
		sut.addCashRegister(aCashRegister2Mock);
		
		Assert.assertEquals(null, sut.getNextCashRegisterFor(aProductListMock));	
		
	}
	
	@Test
	public void testGettingTheWaitingTimeWithTwoCashRegisterWith10secondsAndTheOtherWith8SecondsReturns8Seconds(){
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
				
	}
	
	@Test
	public void testAnUserWithItsProductListIsAddedToTheCashRegisterWithTheLesserTime(){
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegisterMock = Mockito.mock(CashRegister.class);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		InQueueUser anInQueueUserMock = Mockito.mock(InQueueUser.class);
		
		Mockito.when(anInQueueUserMock.getProductList()).thenReturn(aProductListMock);
		Mockito.when(aCashRegisterMock.getWaitingTime()).thenReturn(new Duration(5L));
		Mockito.when(aCashRegisterMock.accepts(aProductListMock)).thenReturn(true);
		
		sut.addCashRegister(aCashRegisterMock);
		sut.newUserToQueue(anInQueueUserMock);
		
		
		Mockito.verify(aCashRegisterMock).next();
		Mockito.verify(anInQueueUserMock).getProductList();
	}
	
	@Test
	public void testChangeACashRegisterToClose(){
		CashRegisterManager sut = new CashRegisterManager();
		
		CashRegister aCashRegisterMock = Mockito.mock(CashRegister.class);
		
		CloseFilter newCloseFilter = new CloseFilter(); 
		
		sut.addCashRegister(aCashRegisterMock);
		sut.changeFilterFor(0, newCloseFilter);
		
		Mockito.verify(aCashRegisterMock).useFilter(newCloseFilter);
	}
	
}
