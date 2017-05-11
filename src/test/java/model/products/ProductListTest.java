package model.products;


import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import model.offers.CategoryOffer;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import util.Category;
import util.Money;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import builders.ProductBuilder;

import static org.mockito.Mockito.*;

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
	public void testWhenIRemoveAProductThatISelectThenItIsRemovedFromMyList() throws ProductIsAlreadySelectedException, ProductDoesNotExistOnListException, MoneyCannotSubstractException {
		
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
	public void testWhenITryToRemoveAProductThatIDidntSelectThenAnExceptionIsRaised() throws ProductDoesNotExistOnListException, ProductIsAlreadySelectedException, MoneyCannotSubstractException{
		
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

	@Test
	public void testGettingTheProcessingTimeIsTheSumOfTheProcessingTimeOfAllMyProducts(){
		
		ProductList aProductList = new ProductList();
		
		SelectedProduct aSelectedProduct1Mock = Mockito.mock(SelectedProduct.class);
		SelectedProduct aSelectedProduct2Mock = Mockito.mock(SelectedProduct.class);
		
		Mockito.when(aSelectedProduct1Mock.getProcessingTime()).thenReturn(new Duration(5000L));
		Mockito.when(aSelectedProduct2Mock.getProcessingTime()).thenReturn(new Duration(5000L));
		
		aProductList.addProductToList(aSelectedProduct1Mock);
		aProductList.addProductToList(aSelectedProduct2Mock);
		
		Assert.assertEquals(new Duration(10000L), aProductList.getProcessingTime());
		
	}
	
	@Test
	public void testGettingTheProductsQuantityIsTheSumOfTheQuantityForEachSelectedProductQuantity(){
		ProductList aProductList = new ProductList();
		
		SelectedProduct aSelectedProduct1Mock = Mockito.mock(SelectedProduct.class);
		SelectedProduct aSelectedProduct2Mock = Mockito.mock(SelectedProduct.class);
		
		Mockito.when(aSelectedProduct1Mock.getQuantity()).thenReturn(10);
		Mockito.when(aSelectedProduct2Mock.getQuantity()).thenReturn(10);
		
		aProductList.addProductToList(aSelectedProduct1Mock);
		aProductList.addProductToList(aSelectedProduct2Mock);
		
		Assert.assertEquals(new Integer(20), aProductList.getQuantityOfProducts());
		
	}
	
	@Test
	public void testWhenApplyingAnOfferThenMyTotalAmountIsUpdated() {
		
		ProductList aProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(20,50));
		Mockito.when(anyProductMock.getCategory()).thenReturn(Category.Baked);
		Interval anInterval = new Interval(DateTime.now(), DateTime.now().plusDays(1)); 
		
		CategoryOffer aCategoryOffer = new CategoryOffer(10 , anInterval , Category.Baked);
		
		try {
			aProductList.selectProduct(anyProductMock, 1);
			Money expected = aProductList.getTotalAmount();
			aProductList.applyOffer(aCategoryOffer);
			
			Assert.assertTrue(aProductList.getAppliedOffers().contains(aCategoryOffer));
			Assert.assertEquals(expected.minus(new Money(2,05)) , aProductList.getTotalAmount());
			
		} catch (MoneyCannotSubstractException | ProductIsAlreadySelectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testWhenRemoveAnAppliedOfferThenMyTotalAmountIsUpdated() {
		
		ProductList aProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(20,50));
		Mockito.when(anyProductMock.getCategory()).thenReturn(Category.Baked);
		Interval anInterval = new Interval(DateTime.now(), DateTime.now().plusDays(1)); 
		
		CategoryOffer aCategoryOffer = new CategoryOffer(10 , anInterval , Category.Baked);
		
		try {
			aProductList.selectProduct(anyProductMock, 1);
			Money expected = aProductList.getTotalAmount();
			aProductList.applyOffer(aCategoryOffer);
			aProductList.disapplyOffer(aCategoryOffer);
			
			Assert.assertFalse(aProductList.getAppliedOffers().contains(aCategoryOffer));
			Assert.assertEquals(expected , aProductList.getTotalAmount());
			
		} catch (MoneyCannotSubstractException | ProductIsAlreadySelectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	@Test
	public void testWhenUpdatingProductListThenItEvaluatesTheListComplete() {
		
		ProductList aProductList = new ProductList();
		Product anyProductMock = Mockito.mock(Product.class);
		Mockito.when(anyProductMock.getPrice()).thenReturn(new Money(20,50));
		Mockito.when(anyProductMock.getCategory()).thenReturn(Category.Baked);
		Interval anInterval = new Interval(DateTime.now(), DateTime.now().plusDays(1)); 
		
		CategoryOffer aCategoryOffer = new CategoryOffer(10 , anInterval , Category.Baked);
		
		try {
			
			aProductList.selectProduct(anyProductMock, 3);
			aProductList.applyOffer(aCategoryOffer);
			Money expected = aProductList.getTotalAmount();
			aProductList.update();
			
			Assert.assertEquals(expected , aProductList.getTotalAmount());
			
		} catch (ProductIsAlreadySelectedException | MoneyCannotSubstractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testWhenRemovingAProductFromProductListThenItReEvaluatesTheListComplete() {
		
		ProductList aProductList = new ProductList();
		Product aProduct = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Marolio")
			.withCategory(Category.Baked)
			.withPrice(new Money(20,50))
			.withStock(31)
			.build();
		Product bProduct = new ProductBuilder()
			.withName("Atun")
			.withBrand("La Campagnola")
			.withCategory(Category.Meat)
			.withPrice(new Money(12,76))
			.withStock(22)
			.build();

		Interval anInterval = new Interval(DateTime.now(), DateTime.now().plusDays(1)); 
		
		CategoryOffer aCategoryOffer = new CategoryOffer(15 , anInterval , Category.Baked);
		
		try {
			
			aProductList.selectProduct(bProduct, 1);
			Money expected = aProductList.getTotalAmount();
			
			aProductList.selectProduct(aProduct, 1);
			aProductList.applyOffer(aCategoryOffer);
			
			Assert.assertTrue(aProductList.getAppliedOffers().contains(aCategoryOffer));
			
			aProductList.removeProduct(aProduct);
			
			Assert.assertEquals( expected , aProductList.getTotalAmount() );
		} catch (ProductIsAlreadySelectedException | MoneyCannotSubstractException | ProductDoesNotExistOnListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
