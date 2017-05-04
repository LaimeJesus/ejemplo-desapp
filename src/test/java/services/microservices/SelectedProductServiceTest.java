package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.products.SelectedProduct;
import services.microservices.SelectedProductService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class SelectedProductServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.selectedproductservice")
    private SelectedProductService selectedProductService;

    @Test
    public void testSelectedProductCanBeSaved(){
    	selectedProductService.save(new SelectedProduct());
        Assert.assertEquals(1, selectedProductService.retriveAll().size());
    }
	
}
