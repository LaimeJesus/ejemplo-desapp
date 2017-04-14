package model.alerts;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.products.Product;
import model.products.ProductList;
import util.Money;

public class PriceAlertTest {

	@Test
	public void testWhenAProductListWithTotalCostOf200OfExceedTheAlertLimitOf100ThenPriceAlertCanDisplayAlert(){
		PriceAlert newPriceAlert = new PriceAlert(new Money(10,0), true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aProductListMock.getTotalAmount()).thenReturn(new Money(20,0));
		
		Assert.assertTrue(newPriceAlert.canDisplayAlert(aProductListMock));
		Mockito.verify(aProductListMock).getTotalAmount();
	}
	
	@Test
	public void testWhenAProductListWithTotalCostOf100OfExceedTheAlertLimitOf150ThenPriceAlertCanDisplayAlert(){
		PriceAlert newPriceAlert = new PriceAlert(new Money(10,0), true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock = Mockito.mock(Product.class);
		Integer aQuantity = 5;
		
		Mockito.when(aProductListMock.getTotalAmount()).thenReturn(new Money(10,0));
		Mockito.when(aProductMock.getPrice()).thenReturn(new Money(1,0));
		
		Assert.assertTrue(newPriceAlert.canDisplayAlert(aProductListMock, aProductMock, aQuantity));
		Mockito.verify(aProductListMock).getTotalAmount();
		Mockito.verify(aProductMock).getPrice();
	}
	
	
}
