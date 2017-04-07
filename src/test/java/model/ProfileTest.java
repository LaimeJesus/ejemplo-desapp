package model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import builders.ProductBuilder;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.ProductListAlreadyCreatedException;
import util.Money;

public class ProfileTest {

	@Test
	public void testWhenICreateProfileThenThereIsNoProductListDefined() {
		
		Profile aProfile = new Profile();
		
		Assert.assertTrue(aProfile.getAllProductList().size() == 0);
	}
	
	@Test
	public void testWhenICreateMultipleProductListThenICanSeeThemAll() {
	
		Profile aProfile = new Profile();
			
		try {
			aProfile.createProductList("Saturday Night");
			aProfile.createProductList("Timmy's Birthday");
			aProfile.createProductList("Soccer Game");
			
			Assert.assertTrue(aProfile.getAllProductList().size() == 3);
			
		} catch (ProductListAlreadyCreatedException e) {
			Assert.fail();
		}		
	}
	
	
	@Test 
	public void testWhenITryToCreateTwoListsWhitSameNameThenIGotAnError() throws ProductListAlreadyCreatedException {
		
		Profile aProfile = new Profile();
				
		try {
			aProfile.createProductList("Saturday Night");
			aProfile.createProductList("Saturday Night");
			
			Assert.fail();
		} catch (ProductListAlreadyCreatedException e) {
			
		}
			
	}
	
	@Test
	public void testWhenIAskForAListThatDoesnotExistItReturnsFalse() {
		
		Profile aProfile = new Profile();
		Assert.assertFalse(aProfile.listAlreadyExist("Inexistent List"));
		
	}
	
	@Test
	public void testWhenIHadAListCreatedICantSeeTheCostOfTheList() {
		
		Profile aProfile = new Profile();
		ProductBuilder productBuilder = new ProductBuilder();
		
		Money aPrice = new Money(31,24);
		Product aProductWithPrice = productBuilder
				.withPrice(aPrice)
				.build();
				
		try {
			
			aProfile.createProductList("Timmy's Birthday");
			aProfile.addProductToList("Timmy's Birthday", aProductWithPrice, 1);
			
			Money expected = new Money(31,24);
			
			Assert.assertEquals(expected, aProfile.getCostOfList("Timmy's Birthday"));
			
		} catch (ProductIsAlreadySelectedException | ProductListAlreadyCreatedException e) {
			fail();
		}
	}

}
