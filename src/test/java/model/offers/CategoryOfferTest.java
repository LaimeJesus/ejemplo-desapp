package model.offers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.ProductIsAlreadySelectedException;
import model.offers.CategoryOffer;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import util.Category;
import util.Money;

public class CategoryOfferTest {

	@Test
	public void testWhenTheCategoryOfferNeedThePrevioysPriceThenAsksToProductList() {
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock1 = Mockito.mock(Product.class);
		Product aProductMock2 = Mockito.mock(Product.class);
		
		Mockito.when(aProductMock1.getCategory()).thenReturn(Category.Baked);
		Mockito.when(aProductMock2.getCategory()).thenReturn(Category.Fruit);
		
		Mockito.when(aProductMock1.getPrice()).thenReturn(new Money(12,30));
		Mockito.when(aProductMock2.getPrice()).thenReturn(new Money(52,30));
		
		SelectedProduct aSelectedProductMock1 = Mockito.mock(SelectedProduct.class);
		SelectedProduct aSelectedProductMock2 = Mockito.mock(SelectedProduct.class);
		
		Mockito.when(aSelectedProductMock1.getProduct()).thenReturn(aProductMock1);
		Mockito.when(aSelectedProductMock2.getProduct()).thenReturn(aProductMock2);
		Mockito.when(aSelectedProductMock1.getFinalPrice()).thenReturn(new Money(3,50));
		Mockito.when(aSelectedProductMock2.getFinalPrice()).thenReturn(new Money(27,25));
		
		List<SelectedProduct> selected = new ArrayList<SelectedProduct>();
		selected.add(aSelectedProductMock1);
		selected.add(aSelectedProductMock2);
		
		Mockito.when(aProductListMock.getTotalAmount()).thenReturn(new Money(24,60));
		Mockito.when(aProductListMock.getAllProducts()).thenReturn(selected);
		
		DateTime today = DateTime.now();
		DateTime tomorrow = today.plusDays(1);
		Interval anInterval = new Interval(today, tomorrow);
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15, anInterval, Category.Baked);
		
		Money expected = new Money(3,50);
		
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
