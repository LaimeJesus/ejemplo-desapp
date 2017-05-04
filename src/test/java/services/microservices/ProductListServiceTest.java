package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.offers.CategoryOffer;
import model.offers.CrossingOffer;
import model.products.ProductList;
import services.general.GeneralOfferService;
import services.microservices.ProductListService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class ProductListServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.productlistservice")
    private ProductListService productListService;
	
	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;
	

    @Test
    public void testProductListCanBeSaved(){
    	Integer expected = productListService.retriveAll().size();
    	productListService.save(new ProductList());
        Assert.assertEquals(expected+1, productListService.retriveAll().size());
    }
    
    @Test
    public void testProductListWithOffersCanBeSaved() {
    	
    	ProductList aPl = new ProductList();
    	aPl.applyOffer(new CategoryOffer());
    	aPl.applyOffer(new CrossingOffer());
    	
    	Integer expected = generalOfferService.retriveAll().size();
    	productListService.save(aPl);
    	
    	
    	Assert.assertEquals(expected+2 , generalOfferService.retriveAll().size() );
    	
    }
	
}
