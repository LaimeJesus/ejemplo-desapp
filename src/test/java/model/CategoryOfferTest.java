package model;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import util.Category;
import util.Money;

public class CategoryOfferTest {

	@Test
	public void testWhenTheCategoryOfferNeedThePrevioysPriceThenAsksToProductList() {
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Mockito.when(aProductListMock.getTotalAmount()).thenReturn(new Money(156,76));
		
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anInterval = new Interval(today, tomorrow);
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15, anInterval, Category.Baked);
		
		Money expected = new Money(156,76);
		
		assertEquals(expected, aCategoryOffer.getPreviousPrice(aProductListMock));
		
	}

}
