package services.microservices;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import builders.ProductBuilder;
import model.products.Product;
import services.microservices.ProductService;
import util.Category;
import util.Money;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@Before
	public void setUp() {
		productService.deleteAll();
	}
	
	@Test
	public void testProductCanBeSaved(){
		productService.save(new Product());
		Assert.assertEquals(1, productService.retriveAll().size());
		productService.deleteAll();
	}
	
	@Test
	public void testWhenISaveAProductThenReceiveAnId() {
		
		Product productToSave = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Marolio")
			.withCategory(Category.Baked)
			.withPrice(new Money(15,50))
			.withStock(45)
			.withProcessingTime(new Duration(1L))
			.build();
		
		productService.save(productToSave);
		
		Assert.assertTrue( productService.retriveAll().contains(productToSave));
		
		productService.delete(productToSave);
	}
	
}

