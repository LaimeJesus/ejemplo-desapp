package model;


import exceptions.ProductDoesNotExistOnListException;
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
	public void testWhenITakeANewProductThenIsOnMyList() throws ProductIsAlreadySelectedException {
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(2,43));
		
		someProductList.selectProduct(anyProductMock, 5);
			
		assertTrue(someProductList.thisProductIsSelected(anyProductMock));		
	}
	
	@Test (expected = ProductIsAlreadySelectedException.class)
	public void testWhenITakeAProductIAlreadySelectThenAnExceptionIsThrown() throws ProductIsAlreadySelectedException {
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(2,43));
		someProductList.selectProduct(anyProductMock, 5);
		someProductList.selectProduct(anyProductMock, 2);
		
	}
	
	@Test
	public void testWhenIRemoveAProductThatISelectThenItIsRemovedFromMyList() throws ProductIsAlreadySelectedException, ProductDoesNotExistOnListException {
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getBrand()).thenReturn("anyBrand");
		Mockito.when(anyProductMock.getName()).thenReturn("anyName");
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(2,43));
		
		someProductList.selectProduct(anyProductMock, 5);
		
		someProductList.removeProduct(anyProductMock);
			
		assertFalse(someProductList.thisProductIsSelected(anyProductMock));
		
	}
	
	@Test (expected = ProductDoesNotExistOnListException.class)
	public void testWhenITryToRemoveAProductThatIDidntSelectThenAnExceptionIsRaised() throws ProductDoesNotExistOnListException{
		
		ProductList someProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		
		someProductList.removeProduct(anyProductMock);
			
		assertFalse(someProductList.thisProductIsSelected(anyProductMock));
		
	}
	
	@Test 
	public void testWhenTwoListHasSameNameThenTheyAreEqual() {
		
		ProductList someProductList = new ProductList();
		someProductList.setName("First List");
		ProductList anotherProductList = new ProductList();
		anotherProductList.setName("First List");
		
		assertTrue(someProductList.equals(anotherProductList));
		
	}
	
	@Test 
	public void testWhenTwoListHasDifferentNamesThenTheyAreNotEqual() {
		
		ProductList someProductList = new ProductList();
		someProductList.setName("First List");
		ProductList anotherProductList = new ProductList();
		anotherProductList.setName("Also First List");
		
		assertFalse(someProductList.equals(anotherProductList));
		
	}

}
