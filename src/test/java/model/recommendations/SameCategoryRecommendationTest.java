package model.recommendations;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import model.Product;
import util.Category;

public class SameCategoryRecommendationTest {

	@Test
	public void testWhenGivenAListOfProductsThenADictionaryDataIsProcessed() {
		
		Product productOneMock = Mockito.mock(Product.class);
		Product productTwoMock = Mockito.mock(Product.class);
		Product productThreeMock = Mockito.mock(Product.class);
		Product productFourMock = Mockito.mock(Product.class);
		Product productFiveMock = Mockito.mock(Product.class);
		
		List<Product> products = new ArrayList<Product>();
		List<Product> expected = new ArrayList<Product>();
		
		products.add(productOneMock);
		products.add(productTwoMock);
		products.add(productThreeMock);
		products.add(productFourMock);
		products.add(productFiveMock);
		
		expected.add(productOneMock);
		expected.add(productFourMock);
		
		
		Mockito.when(productOneMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productTwoMock.getCategory()).thenReturn(Category.Cleaning);
		Mockito.when(productThreeMock.getCategory()).thenReturn(Category.Dairy);
		Mockito.when(productFourMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productFiveMock.getCategory()).thenReturn(Category.Baked);
		
		
		SameCategoryRecommendation scr = new SameCategoryRecommendation();
		
		scr.setElementsToProcess(products);
		
		scr.processData();
		
		assertTrue(scr.getCategoryMapping().get(Category.Drink).containsAll(expected));
		
		
	}
	
	@Test
	public void testWhenGivenAProcessedDictionaryDataThenICanGetARecommendation() {
		
		Product productOneMock = Mockito.mock(Product.class);
		Product productTwoMock = Mockito.mock(Product.class);
		Product productThreeMock = Mockito.mock(Product.class);
		Product productFourMock = Mockito.mock(Product.class);
		Product productFiveMock = Mockito.mock(Product.class);
		
		List<Product> products = new ArrayList<Product>();
		List<Product> expected = new ArrayList<Product>();
		
		products.add(productOneMock);
		products.add(productTwoMock);
		products.add(productThreeMock);
		products.add(productFourMock);
		products.add(productFiveMock);
		
		expected.add(productOneMock);
		expected.add(productFourMock);
		
		
		Mockito.when(productOneMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productTwoMock.getCategory()).thenReturn(Category.Cleaning);
		Mockito.when(productThreeMock.getCategory()).thenReturn(Category.Dairy);
		Mockito.when(productFourMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productFiveMock.getCategory()).thenReturn(Category.Baked);
		
		
		SameCategoryRecommendation scr = new SameCategoryRecommendation();
		
		scr.setElementsToProcess(products);
		
		scr.processData();
		
		Recommendation aRecommendation = scr.generateRecommendation(productOneMock, 3);
		
		assertTrue(aRecommendation.getRecommended().containsAll(expected));
		
		
	}
	
	@Test
	public void testWhenGivenALimitedProcessedDictionaryDataThenICanGetALimitedRecommendation() {
		
		Product productOneMock = Mockito.mock(Product.class);
		Product productTwoMock = Mockito.mock(Product.class);
		Product productThreeMock = Mockito.mock(Product.class);
		Product productFourMock = Mockito.mock(Product.class);
		Product productFiveMock = Mockito.mock(Product.class);
		
		List<Product> products = new ArrayList<Product>();
		List<Product> expected = new ArrayList<Product>();
		
		products.add(productOneMock);
		products.add(productTwoMock);
		products.add(productThreeMock);
		products.add(productFourMock);
		products.add(productFiveMock);
		
		expected.add(productOneMock);
		expected.add(productFourMock);
		
		
		Mockito.when(productOneMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productTwoMock.getCategory()).thenReturn(Category.Cleaning);
		Mockito.when(productThreeMock.getCategory()).thenReturn(Category.Dairy);
		Mockito.when(productFourMock.getCategory()).thenReturn(Category.Drink);
		Mockito.when(productFiveMock.getCategory()).thenReturn(Category.Baked);
		
		
		SameCategoryRecommendation scr = new SameCategoryRecommendation();
		
		scr.setElementsToProcess(products);
		
		scr.processData();
		
		Recommendation aRecommendation = scr.generateRecommendation(productOneMock, 1);
		
		assertTrue(aRecommendation.getRecommended().contains(productOneMock));
		
		
	}


}
