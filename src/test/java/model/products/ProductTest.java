package model.products;

import org.junit.Assert;
import org.junit.Test;

import builders.ProductBuilder;
import model.products.Product;
import util.Category;
import util.Money;

public class ProductTest {
	
	@Test
	public void testWhenAProductIsCreatedThenAllFieldAreCorrect() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("La Salteña")
				.withPrice(new Money(3,50))
				.withStock(500)
				.withCategory(Category.Perfumery)
				.build();
		
		String expected = "La Salteña";
		String actual = aProduct.getBrand();
		
		Assert.assertEquals(expected , actual);
		
	}
	
	
	@Test
	public void testWhenICompareTheSameProductThenTheyAreTheSame() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("Marolio")
				.build();
		
		Product anotherProduct = productBuilder
				.withName("Arroz")
				.withBrand("Marolio")
				.build();
		
		Assert.assertEquals(aProduct, anotherProduct);
	}
	
	@Test
	public void testWhenCompareDiferentProductsThenTheyAreNotTheSame() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		ProductBuilder productBuilder2 = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("Maruchan")
				.build();
		
		Product anotherProduct = productBuilder2
				.withName("Fideos")
				.withBrand("Marolio")
				.build();
		
		Assert.assertNotEquals(aProduct, anotherProduct);
	}
	
	@Test
	public void testWhenICompareAProductAndSomethingDiferentThenTheyAreNotEquals() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("Marolio")
				.build();
		
		//Assert.assertFalse(aProduct.equals("Im Not A Product"));
		Assert.assertFalse("Im Not A Product".equals(aProduct));
		
	}
	
	@Test
	public void testWhenComparingProductWithTheSameBrandButDifferentNameThenTheyAreNotTheSame() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		ProductBuilder productBuilder2 = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("Maruchan")
				.build();
		
		Product anotherProduct = productBuilder2
				.withName("Fideos")
				.withBrand("Maruchan")
				.build();
		
		Assert.assertNotEquals(aProduct, anotherProduct);
		
	}
	
	@Test
	public void testWhenComparingAProductWIthNullThenTheyAreNotTheSame() {
		
		ProductBuilder productBuilder = new ProductBuilder();
		
		Product aProduct = productBuilder
				.withName("Arroz")
				.withBrand("Maruchan")
				.build();
		
		Assert.assertNotEquals(aProduct, null);
		
	}
	
}
