package services.microservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import builders.ProductBuilder;
import model.products.Product;
import model.products.SelectedProduct;
import services.microservices.SelectedProductService;
import util.Category;
import util.Money;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class SelectedProductServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.selectedproductservice")
    private SelectedProductService selectedProductService;

	@Autowired
    @Qualifier("services.microservices.productservice")
    private ProductService productService;
	
	@Before
	public void setUp() {
		selectedProductService.deleteAll();
	}
	
    @Test
    public void testSelectedProductCanBeSaved(){
    	Integer expected = selectedProductService.count();
    	
    	Product someProduct = new ProductBuilder()
    		.withName("Arroz")
    		.withCategory(Category.Baked)
    		.withBrand("Marolio")
    		.withPrice(new Money(3,5))
    		.withStock(100)
    		.build();
    	
    	productService.save(someProduct);
    	
    	SelectedProduct someSelected = new SelectedProduct();
    	someSelected.setQuantity(1);
    	someSelected.setProduct(someProduct);
    	
    	selectedProductService.save(someSelected);
        Assert.assertEquals(expected+1, selectedProductService.retriveAll().size());
    }
	
}
