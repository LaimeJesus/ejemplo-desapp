package model.users;


import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import builders.ProductBuilder;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import model.alerts.PriceAlert;
import model.products.Product;
import model.products.ProductList;
import model.users.Profile;
import util.Money;

public class ProfileTest {

	@Test
	public void testWhenICreateProfileThenThereIsNoProductListDefined() {
		
		Profile aProfile = new Profile();
		
		Assert.assertTrue(aProfile.getAllProductList().size() == 0);
	}
	
	@Test
	public void testWhenICreateMultipleProductListThenICanSeeThemAll() throws ProductListAlreadyCreatedException {
	
		Profile aProfile = new Profile();
			
		aProfile.createProductList("Saturday Night");
		aProfile.createProductList("Timmy's Birthday");
		aProfile.createProductList("Soccer Game");
		
		Assert.assertTrue(aProfile.getAllProductList().size() == 3);		
	}
	
	
	@Test(expected=ProductListAlreadyCreatedException.class)
	public void testWhenITryToCreateTwoListsWhitSameNameThenIGotAnError() throws ProductListAlreadyCreatedException {
		
		Profile aProfile = new Profile();
				
		aProfile.createProductList("Saturday Night");
		aProfile.createProductList("Saturday Night");
	}
	
	@Test
	public void testWhenIAskForAListThatDoesnotExistItReturnsFalse() {
		
		Profile aProfile = new Profile();
		Assert.assertFalse(aProfile.listAlreadyExist("Inexistent List"));
		
	}
	
	@Test
	public void testWhenIHadAListCreatedICantSeeTheCostOfTheList() throws ProductListAlreadyCreatedException, ProductIsAlreadySelectedException {
		
		Profile aProfile = new Profile();
		ProductBuilder productBuilder = new ProductBuilder();
		
		Money aPrice = new Money(31,24);
		Product aProductWithPrice = productBuilder
				.withPrice(aPrice)
				.build();
				
		
		aProfile.createProductList("Timmy's Birthday");
		aProfile.addProductToList("Timmy's Birthday", aProductWithPrice, 1);
		
		Money expected = new Money(31,24);
		
		Assert.assertEquals(expected, aProfile.getCostOfList("Timmy's Birthday"));
			
	}

	@Test
	public void testCheckIfMyAlertsCanDisplaySomeAlertForAProductListReturnsTrue(){
		Profile aProfile = new Profile();
		
		PriceAlert aPriceAlertMock = Mockito.mock(PriceAlert.class);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aPriceAlertMock.canDisplayAlert(aProductListMock)).thenReturn(true);
		aProfile.addNewAlert(aPriceAlertMock);
		
		Mockito.doNothing().when(aPriceAlertMock).activate();
		aProfile.activate(aPriceAlertMock);
		
		Assert.assertTrue(aProfile.checkCanDisplayAlerts(aProductListMock));
		
	}
	
	@Test
	public void testCheckIfMyAlertsCanDisplaySomeAlertForAProductListReturnsFalse(){
		Profile aProfile = new Profile();
		
		PriceAlert aPriceAlertMock = Mockito.mock(PriceAlert.class);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Mockito.when(aPriceAlertMock.canDisplayAlert(aProductListMock)).thenReturn(false);
		aProfile.addNewAlert(aPriceAlertMock);
		
		Mockito.doNothing().when(aPriceAlertMock).shutdown();
		aProfile.shutdown(aPriceAlertMock);
		
		Assert.assertFalse(aProfile.checkCanDisplayAlerts(aProductListMock));
		
	}	

	@Test
	public void testCheckIfMyAlertsCanDisplaySomeAlertForAProductListWhenIsTryingToAddANewProductReturnsTrue(){
		Profile aProfile = new Profile();
		
		PriceAlert aPriceAlertMock = Mockito.mock(PriceAlert.class);
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		Product aProductMock = Mockito.mock(Product.class);
		Integer aQuantity = 1;
		
		Mockito.when(aPriceAlertMock.canDisplayAlert(aProductListMock, aProductMock, aQuantity)).thenReturn(true);
		aProfile.addNewAlert(aPriceAlertMock);
		
		Mockito.doNothing().when(aPriceAlertMock).shutdown();
		aProfile.shutdown(aPriceAlertMock);
		
		Assert.assertTrue(aProfile.checkCanDisplayAlerts(aProductListMock, aProductMock, aQuantity));
		
	}	
}
