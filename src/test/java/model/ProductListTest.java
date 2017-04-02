package model;


import exceptions.ProductIsAlreadySelectedException;
import util.Money;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

public class ProductListTest {

	@Test
	public void testWhenAProductListIsCreatedThenItsProductsAreEmptyYet() {
		
		ProductList someProductList = new ProductList();
		
		assertTrue(someProductList.isEmpty());
		
	}
	
	
	@Test
	public void testWhenITakeANewProductThenIsOnMyList() {
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(2,43));
		
		try {
			someProductList.selectProduct(anyProductMock, 5);
			
			assertTrue(someProductList.thisProductIsSelected(anyProductMock));
		} catch (ProductIsAlreadySelectedException e) {
			fail();
		}
		
	}
	
	@Test (expected = ProductIsAlreadySelectedException.class)
	public void testWhenITakeAProductIAlreadySelectThenAnExceptionIsThrown() throws ProductIsAlreadySelectedException {
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(2,43));
		someProductList.selectProduct(anyProductMock, 5);
		someProductList.selectProduct(anyProductMock, 2);
		
	}

}
