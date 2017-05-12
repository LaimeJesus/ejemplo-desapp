package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.offers.CrossingOffer;
import services.microservices.CrossingOfferService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class CrossingOfferServiceTest{

	
	
	
	
	@Autowired
    @Qualifier("services.microservices.crossingofferservice")
    private CrossingOfferService crossingOfferService;

    @Test
    public void testCrossingOfferCanBeSaved(){
    	Integer expected = crossingOfferService.retriveAll().size();
    	crossingOfferService.save(new CrossingOffer());
        Assert.assertEquals(expected+1, crossingOfferService.retriveAll().size());
        
        
        crossingOfferService.deleteAll();
    }
	
}
