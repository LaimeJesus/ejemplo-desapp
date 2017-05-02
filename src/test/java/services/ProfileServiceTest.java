package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.alerts.PriceAlert;
import model.alerts.UmbralAlert;
import model.registers.PurchaseRecord;
import model.users.Profile;
import services.microservices.ProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ProfileServiceTest {
	
	@Autowired
	@Qualifier("services.microservices.profileservice")
	private ProfileService profileService;
	
	@Test
	public void testAnEmptyProfileCanBeSaved(){
		Profile aProfile = new Profile();
		profileService.save(aProfile);
		Assert.assertEquals(1, profileService.retriveAll().size());
		profileService.delete(aProfile);
		Assert.assertEquals(0, profileService.retriveAll().size());
	}
	
	@Test
	public void testAProfileCanBeSavedWithAlerts(){
		Profile aProfile = new Profile();
		PriceAlert priceAlert = new PriceAlert();
		
		UmbralAlert umbralAlert = new UmbralAlert();
		
		aProfile.addNewAlert(priceAlert);
		aProfile.addNewAlert(umbralAlert);
		
		profileService.save(aProfile);
		
		Assert.assertEquals(2, profileService.find(aProfile).getAlerts().size());
		
		profileService.delete(aProfile);
		Assert.assertEquals(0, profileService.retriveAll().size());
	}
	@Test
	public void testAProfileCanBeSavedWithPurchaseRecords(){
		Profile aProfile = new Profile();
		PurchaseRecord purchaseRecord = new PurchaseRecord();
		
		aProfile.addNewPurchaseToHistory(purchaseRecord);
		
		profileService.save(aProfile);
		
		Assert.assertEquals(1, profileService.find(aProfile).getPurchaseRecords());
		
		profileService.delete(aProfile);
		Assert.assertEquals(0, profileService.retriveAll().size());
	}

		
}
