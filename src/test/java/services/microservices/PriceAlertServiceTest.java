package services.microservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.alerts.PriceAlert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class PriceAlertServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.pricealertservice")
	private PriceAlertService priceAlertService;
	
	@Before
	public void setUp() {
		priceAlertService.deleteAll();
	}
	
	@Test
    public void testPriceAlertCanBeSaved(){
    	Integer expected = priceAlertService.retriveAll().size();
    	priceAlertService.save(new PriceAlert());
        Assert.assertEquals(expected+1, priceAlertService.retriveAll().size());
        
        priceAlertService.deleteAll();
    }
	
}
