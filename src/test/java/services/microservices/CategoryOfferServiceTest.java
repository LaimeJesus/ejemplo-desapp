package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.offers.CategoryOffer;
import services.microservices.CategoryOfferService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class CategoryOfferServiceTest{

	
	@Autowired
    @Qualifier("services.microservices.categoryofferservice")
    private CategoryOfferService categoryOfferService;

    @Test
    public void testCategoryOfferCanBeSaved(){
    	Integer expected = categoryOfferService.retriveAll().size();
    	categoryOfferService.save(new CategoryOffer());
        Assert.assertEquals(expected+1, categoryOfferService.retriveAll().size());
        
        categoryOfferService.deleteAll();
        
    }
	
}
