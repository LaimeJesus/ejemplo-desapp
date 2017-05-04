package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.products.ProductList;
import services.microservices.ProductListService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class ProductListServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.productlistservice")
    private ProductListService productListService;

    @Test
    public void testProductListCanBeSaved(){
    	productListService.save(new ProductList());
        Assert.assertEquals(1, productListService.retriveAll().size());
    }
	
}
