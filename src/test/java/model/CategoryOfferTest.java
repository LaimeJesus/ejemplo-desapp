package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import antlr.collections.List;
import model.offers.CategoryOffer;
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
	
	@Test
	public void testWhenAListHasAtLeastOneProductOfTheCategoryThenTheOfferCanBeApplied() {
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anInterval = new Interval(today, tomorrow);
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15, anInterval, Category.Baked);
		
		aCategoryOffer.meetRequirements(aProductListMock);
		
		Mockito.verify(aProductListMock).getAllProducts();
		
	}

}
