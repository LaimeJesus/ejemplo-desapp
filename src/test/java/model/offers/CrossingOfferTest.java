package model.offers;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.ProductIsAlreadySelectedException;
import model.Product;
import model.ProductList;
import util.Money;

public class CrossingOfferTest {

	@Test
	public void testWhenIGotACrossingOfferThenThePreviousPriceIsTheRegularPrice() {
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Product aProductMock = Mockito.mock(Product.class);
		Mockito.when(aProductMock.getPrice()).thenReturn(new Money(3,50));
		
		Integer maxQuantity = 3;
		
		CrossingOffer aCrossingOffer = new CrossingOffer(23, aProductMock, maxQuantity, 2, anIntervalMock);
		
		Money expected = (new Money(3,50)).times(maxQuantity);
		
		assertEquals(expected, aCrossingOffer.getPreviousPrice(aProductListMock));
		
	}
	
	@Test
	public void testWhenTheListHasMaxQuantityOfProductThenMeetsTheRequirements() {
		
		ProductList aProductList = new ProductList();
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anIntervalMock = new Interval(today, tomorrow);
		
		Product aProductMock = Mockito.mock(Product.class);
		Mockito.when(aProductMock.getPrice()).thenReturn(new Money(2,75));
		
		Integer maxQuantity = 3;
		
		CrossingOffer aCrossingOffer = new CrossingOffer(23, aProductMock, maxQuantity, 2, anIntervalMock);
		
		try {
			aProductList.selectProduct(aProductMock, maxQuantity);
			assertTrue(aCrossingOffer.meetRequirements(aProductList));
		} catch (ProductIsAlreadySelectedException e) {
		}
		
		
	}

}
