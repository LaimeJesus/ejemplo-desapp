package model;

import org.mockito.*;

import exceptions.ProductIsAlreadySelectedException;
import util.Money;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductListTest {

	@Test
	public void testWhenAProductListIsCreatedThenItsProductsAreEmptyYet() {
		
		ProductList someProductList = new ProductList();
		
		assertTrue(someProductList.isEmpty());
		
	}

}
