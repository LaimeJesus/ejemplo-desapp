package model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductIsAlreadySelectedException;
import util.Money;

public class CombinationOfferTest {

	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskThePriceWithoutTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15);
		
		Money expected = new Money(41,77);
		
		assertEquals(aOffer.getPreviousPrice(), expected);
		
	}
	
	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15);
		
		Money expected = new Money(6,30);
		
		assertEquals(aOffer.getDiscount(aOffer.getDiscountRate()), expected);
		
	}
	
	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskThePriceWithTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15);
		
		Money expected = new Money(35,47);
		
		try {
			assertEquals(aOffer.getFinalPrice(), expected);
		} catch (MoneyCannotSubstractException e) {
			fail();
		}
		
	}
	
	@Test
	public void testWhenAnOfferWasntAppliedThenIfTheConditionsAreFineICanApplyTheOffer() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15);
		
		try {
			someProductList.selectProduct(firstProductMock, 1);
			someProductList.selectProduct(secondProductMock, 1);
			
			assertTrue(someProductList.isApplicable(aOffer));
			
		} catch (ProductIsAlreadySelectedException e) {
			fail();
		};
		
	}
	
	@Test
	public void testWhenAnOfferWasAppliedThenICantApplyTheOffer() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15);
		
		try {
			someProductList.selectProduct(firstProductMock, 1);
			someProductList.selectProduct(secondProductMock, 1);
			
			someProductList.applyOffer(aOffer);
			
			assertFalse(someProductList.isApplicable(aOffer));
			
		} catch (ProductIsAlreadySelectedException e) {
			fail();
		};
		
	}

}
