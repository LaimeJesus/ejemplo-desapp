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
	
	@Test
	public void testWhenMyProductListAddsAValidNewProductMyListIsIncreased() throws ProductIsAlreadySelectedException {
		
		ProductList someProductList = new ProductList();
		
		Product aProduct = Mockito.mock(Product.class);
		Money aMoney = Mockito.mock(Money.class);
		
		Mockito.when(aMoney.times(3)).thenReturn(aMoney);
		Mockito.when(aProduct.getPrice()).thenReturn(aMoney);
		
		someProductList.selectProduct(aProduct, 3);
		
		assertTrue(! someProductList.isEmpty());
	}

}
