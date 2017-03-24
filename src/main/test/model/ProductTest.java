package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProductTest {
	
	@Test
	public void testWhenAProductIsCreatedThenAllFieldAreCorrect() {
		
		///
		Product aProduct = new Product("arroz", "marolio" , "56" , "3,50");
		
		///
		String expected = "marolio";
		String actual = aProduct.getBrand();
		
		///
		assertEquals(expected , actual);
		
	}
}
