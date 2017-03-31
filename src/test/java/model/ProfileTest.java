package model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import exceptions.ProductListAlreadyCreatedException;

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
			fail();
		}		
	}
	
	
	@Test 
	public void testWhenITryToCreateTwoListsWhitSameNameThenIGotAnError() throws ProductListAlreadyCreatedException {
		
		Profile aProfile = new Profile();
				
		try {
			aProfile.createProductList("Saturday Night");
			aProfile.createProductList("Saturday Night");
			
			fail();
		} catch (ProductListAlreadyCreatedException e) {
			
		}
			
	}

}
