package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import util.Money;

public class ProductTest {
	
	@Test
	public void testWhenAProductIsCreatedThenAllFieldAreCorrect() {
		
		///
		Product aProduct = new Product("arroz", "marolio" , "56" , new Money(3,67));
		
		///
		String expected = "marolio";
		String actual = aProduct.getBrand();
		
		///
		assertEquals(expected , actual);
		
	}
	
}
