package model.alerts;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.Product;
import model.ProductList;
import util.Category;

public class UmbralAlertTest {

	@Test
	public void testANewAlertIsOn(){
		UmbralAlert newAlert = new UmbralAlert(Category.Baked, 50.0, true);
		
		newAlert.shutdown();
		
		Assert.assertFalse(newAlert.isOn());
		
		newAlert.activate();
		
		Assert.assertTrue(newAlert.isOn());
		
	}
	
	@Test
	public void testWhenAProductListExceedThePercentageLimitWhileIsTryingToAddANewProductThenUmbralAlertCanDisplayAlert(){
		UmbralAlert newUmbralAlert = new UmbralAlert(Category.Drink, 50.0, true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock = Mockito.mock(Product.class);
		Integer aQuantity = 5;
		
		Mockito.when(aProductMock.getCategory()).thenReturn(Category.Drink);		
		Mockito.when(aProductListMock.getQuantityOfProducts()).thenReturn(10);
		Mockito.when(aProductListMock.getQuantityOfProducts(Category.Drink)).thenReturn(3);
		
		
		Assert.assertTrue(newUmbralAlert.canDisplayAlert(aProductListMock , aProductMock, aQuantity));
	}
	
	@Test
	public void testWhenAnAlertIsOffThenThisAlertCannotDisplayAnAlert(){
		UmbralAlert newUmbralAlert = new UmbralAlert();
		newUmbralAlert.shutdown();
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock = Mockito.mock(Product.class);
		Integer aQuantity = 10;
		
		Assert.assertFalse(newUmbralAlert.canDisplayAlert(aProductListMock, aProductMock, aQuantity));
	}
	
	@Test
	public void testWhenAProductListExceedThePercentageLimitUmbralALertCanDisplayAlert(){
		UmbralAlert newUmbralAlert = new UmbralAlert(Category.Drink, 50.0, true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aProductListMock.getQuantityOfProducts()).thenReturn(10);
		Mockito.when(aProductListMock.getQuantityOfProducts(Category.Drink)).thenReturn(10);		
		
		Assert.assertTrue(newUmbralAlert.canDisplayAlert(aProductListMock));
	}

	@Test
	public void testWhenAProductListNotExceedThePercentageLimitUmbralALertCannotDisplayAlert(){
		UmbralAlert newUmbralAlert = new UmbralAlert(Category.Drink, 50.0, true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aProductListMock.getQuantityOfProducts()).thenReturn(20);
		Mockito.when(aProductListMock.getQuantityOfProducts(Category.Drink)).thenReturn(5);		
		
		Assert.assertFalse(newUmbralAlert.canDisplayAlert(aProductListMock));
	}
	
	@Test
	public void testWhenAProductListNotExceedThePercentageLimitWhileIsTryingToAddANewProductThenUmbralAlertCannotDisplayAlert(){
		UmbralAlert newUmbralAlert = new UmbralAlert(Category.Drink, 50.0, true);
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock = Mockito.mock(Product.class);
		Integer aQuantity = 5;		
		
		Mockito.when(aProductMock.getCategory()).thenReturn(Category.Drink);		
		Mockito.when(aProductListMock.getQuantityOfProducts()).thenReturn(20);
		Mockito.when(aProductListMock.getQuantityOfProducts(Category.Drink)).thenReturn(0);

		Assert.assertFalse(newUmbralAlert.canDisplayAlert(aProductListMock , aProductMock, aQuantity));
	}	
	
}
