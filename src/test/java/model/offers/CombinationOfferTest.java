package model.offers;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.MoneyCannotSubstractException;
import exceptions.ProductIsAlreadySelectedException;
import model.offers.CombinationOffer;
import model.products.Product;
import model.products.ProductList;
import util.Money;

public class CombinationOfferTest {

	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskThePriceWithoutTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
				
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);
		
		Money expected = new Money(41,77);
		
		assertEquals(aOffer.getPreviousPrice(aProductListMock), expected);
		
	}
	
	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15,anIntervalMock);
		
		Money expected = new Money(6,27);
		
		assertEquals(expected , aOffer.getDiscount(aOffer.getDiscountRate() , aProductListMock));
		
	}
	 
	@Test
	public void testWhenAnCombinationOfferIsCreatedThenICanAskThePriceWithTheDiscount() {
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListMock.getTotalAmount()).thenReturn(new Money(41,77));
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);
		
		Money expected = new Money(35,50);
		
		assertEquals(aOffer.getFinalPrice(aProductListMock , new Money(41,77)), expected);
		
	}
	
	@Test
	public void testWhenAnOfferWasntAppliedThenIfTheConditionsAreFineICanApplyTheOffer() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);
		
		try {
			someProductList.selectProduct(firstProductMock, 1);
			someProductList.selectProduct(secondProductMock, 1);
			
			assertTrue(someProductList.isApplicable(aOffer));
			
		} catch (ProductIsAlreadySelectedException e) {
			fail();
		}
		
	}
	
	@Test
	public void testWhenAnOfferWasAppliedThenICantApplyTheOffer() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);
		
		try {
			someProductList.selectProduct(firstProductMock, 1);
			someProductList.selectProduct(secondProductMock, 1);
			
			System.out.println(someProductList.getTotalAmount());
			
			someProductList.applyOffer(aOffer);
			
			System.out.println(someProductList.getTotalAmount());
			
			assertFalse(someProductList.isApplicable(aOffer));
			
		} catch (ProductIsAlreadySelectedException | MoneyCannotSubstractException e) {
			fail();
		}
		
	}
	
	@Test
	public void testWhenTheTwoProductsHaveBeenSelectedThenTheProductListMeetsTheRequirements() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);
		
		try {
			someProductList.selectProduct(firstProductMock, 3);
			someProductList.selectProduct(secondProductMock, 2);
			
			assertTrue(aOffer.meetRequirements(someProductList));
			
		} catch (ProductIsAlreadySelectedException e) {
		};
		
	}
	
	@Test
	public void testWhenTheTwoProductsHaveNotBeenSelectedThenTheProductListDoesNotMeetsTheRequirements() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);

		assertFalse(aOffer.meetRequirements(someProductList));
		
	}
	
	@Test
	public void testWhenOneOfThemIsSelectedThenTheProductListDoesNotMeetsTheRequirements() {
		
		ProductList someProductList = new ProductList("Example List");
		
		Product firstProductMock = Mockito.mock(Product.class);
		Product secondProductMock = Mockito.mock(Product.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Mockito.when(firstProductMock.getPrice()).thenReturn(new Money(24,56));
		Mockito.when(secondProductMock.getPrice()).thenReturn(new Money(17,21));
		
		CombinationOffer aOffer = new CombinationOffer(firstProductMock, secondProductMock, 15, anIntervalMock);

		try {
			someProductList.selectProduct(firstProductMock, 3);
			
			assertFalse(aOffer.meetRequirements(someProductList));
			
		} catch (ProductIsAlreadySelectedException e) {}
	}

}
