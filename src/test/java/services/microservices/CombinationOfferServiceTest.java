package services.microservices;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.offers.CombinationOffer;
import services.microservices.CombinationOfferService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class CombinationOfferServiceTest{

	
	@Autowired
    @Qualifier("services.microservices.combinationofferservice")
    private CombinationOfferService combinationOfferService;

    @Test
    public void testCombinationOfferCanBeSaved(){
    	
    	Integer expected = combinationOfferService.retriveAll().size();
    	combinationOfferService.save(new CombinationOffer());
        Assert.assertEquals(expected+1 , combinationOfferService.retriveAll().size());

        
        combinationOfferService.deleteAll();
    }
	
}
