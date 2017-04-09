package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import antlr.collections.List;
import exceptions.ProductIsAlreadySelectedException;
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
		
		ProductList aProductList = new ProductList();
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anInterval = new Interval(today, tomorrow);
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15, anInterval, Category.Drink);
		
		Product aProductMock = Mockito.mock(Product.class);
		Mockito.when(aProductMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(aProductMock.getPrice()).thenReturn(new Money(2,3));
		Product a2ProductMock = Mockito.mock(Product.class);
		Mockito.when(a2ProductMock.getCategory()).thenReturn(Category.Cleaning);
		Mockito.when(a2ProductMock.getPrice()).thenReturn(new Money(3,2));
		
		try {
			aProductList.selectProduct(aProductMock, 2);
			aProductList.selectProduct(a2ProductMock, 3);
			
			assertTrue(aCategoryOffer.meetRequirements(aProductList));
		} catch (ProductIsAlreadySelectedException e) {
		}
		
	}
	
	@Test
	public void testWhenAListHasNoneProductOfTheCategoryThenTheOfferCanNotBeApplied()  {
		
		ProductList aProductList = new ProductList();
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anInterval = new Interval(today, tomorrow);
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15, anInterval, Category.Drink);
		
		Product aProductMock = Mockito.mock(Product.class);
		Mockito.when(aProductMock.getCategory()).thenReturn(Category.Dairy);
		Mockito.when(aProductMock.getPrice()).thenReturn(new Money(2,3));
		Product a2ProductMock = Mockito.mock(Product.class);
		Mockito.when(a2ProductMock.getCategory()).thenReturn(Category.Cleaning);
		Mockito.when(a2ProductMock.getPrice()).thenReturn(new Money(3,2));
		
		try {
			aProductList.selectProduct(aProductMock, 2);
			aProductList.selectProduct(a2ProductMock, 3);
			
			assertFalse(aCategoryOffer.meetRequirements(aProductList));
		} catch (ProductIsAlreadySelectedException e) {
		}
		
		
	}

}
