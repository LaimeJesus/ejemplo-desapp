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
import model.products.SelectedProduct;
import services.microservices.ProductService;
import util.Category;
import util.Money;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SelectedProductService selectedProductService;
	
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
	
	//@Test
	public void testWhenDeletingAProductThatIsSelectedThenAllSelectionsAreDeleted() {
		Product product1ToSave = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Molto")
			.withCategory(Category.Baked)
			.withPrice(new Money(15,50))
			.withStock(45)
			.build();
		
		productService.save(product1ToSave);
		
		Integer expected = selectedProductService.count();
	
		SelectedProduct selection1 = new SelectedProduct(product1ToSave , 1);
		SelectedProduct selection2 = new SelectedProduct(product1ToSave , 2);
		SelectedProduct selection3 = new SelectedProduct(product1ToSave , 3);
		
		selectedProductService.save(selection1);
		selectedProductService.save(selection2);
		selectedProductService.save(selection3);
		
		
		for (Product p : productService.retriveAll()) {
			System.out.println("ID : " + p.getId());
			System.out.println("name : " + p.getName());
			System.out.println("brand : " + p.getBrand());
		}
		
		productService.delete(product1ToSave);
		
		Assert.assertEquals(expected, selectedProductService.count());
	
	}
	
	
	
}

