package services.general;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.offers.CategoryOffer;
import model.offers.CrossingOffer;
import model.offers.Offer;
import services.general.GeneralOfferService;
import services.microservices.CategoryOfferService;
import services.microservices.CrossingOfferService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class GeneralOfferServiceTest {
	
	
	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;
	
	@Autowired
    @Qualifier("services.microservices.categoryofferservice")
    private CategoryOfferService categoryOfferService;
	
	@Autowired
    @Qualifier("services.microservices.crossingofferservice")
    private CrossingOfferService crossinfOfferService;
	
	@Before
	public void setUp() {
		generalOfferService.deleteAll();
		categoryOfferService.deleteAll();
		crossinfOfferService.deleteAll();
	}
	
    @Test
    public void testGeneralOfferServiceCanSaveOffer(){
    	Offer someOffer = new CategoryOffer();
    	
    	Integer expected = categoryOfferService.retriveAll().size();
    	generalOfferService.save(someOffer);
    	
    	Assert.assertEquals(expected+1 , categoryOfferService.retriveAll().size());   	
    }
    
    @Test
    public void testGeneralOfferServiceCanSaveDifferentsOffers(){
    	Offer someOffer = new CategoryOffer();
    	Offer anotherOffer = new CrossingOffer();
    	
    	Integer expectedCategory = categoryOfferService.retriveAll().size();
    	generalOfferService.save(someOffer);
    	
    	Integer expectedCrossing = crossinfOfferService.retriveAll().size();
    	generalOfferService.save(anotherOffer);
    	
    	Assert.assertEquals(expectedCategory+1 , categoryOfferService.retriveAll().size());  
    	Assert.assertEquals(expectedCrossing+1 , crossinfOfferService.retriveAll().size());
    }

    
    @After
    public void after(){
    	generalOfferService.deleteAll();
    }
}
