package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.registers.PurchaseRecord;
import services.microservices.PurchaseRecordService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class PurchaseRecordServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.purchaserecordservice")
    private PurchaseRecordService purchaseRecordService;

    @Test
    public void testCategoryOfferCanBeSaved(){
    	purchaseRecordService.save(new PurchaseRecord());
        Assert.assertEquals(1, purchaseRecordService.retriveAll().size());
    }
	
}
