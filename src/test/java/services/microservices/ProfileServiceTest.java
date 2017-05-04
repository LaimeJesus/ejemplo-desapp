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
import model.alerts.UmbralAlert;
import model.registers.PurchaseRecord;
import model.users.Profile;
import model.users.User;
import services.microservices.ProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ProfileServiceTest {
	
	@Autowired
	@Qualifier("services.microservices.profileservice")
	private ProfileService profileService;
	
	@Autowired
	@Qualifier("services.microservices.userservice")
	private UserService userService;
	
	@Autowired
	@Qualifier("services.microservices.umbralalertservice")
	private UmbralAlertService umbralAlertService;
	
	@Autowired
	@Qualifier("services.microservices.pricealertservice")
	private PriceAlertService priceAlertService;
	
	@Before
	public void setUp() {
		profileService.deleteAll();
		userService.deleteAll();
		umbralAlertService.deleteAll();
		priceAlertService.deleteAll();
	}
	
	@Test
	public void testAnEmptyProfileCanBeSaved(){
		Integer expected = profileService.retriveAll().size();

		User aUser = new User();
		Profile aProfile = new Profile();
		aUser.setProfile(aProfile);
		userService.save(aUser);
		
		
		Assert.assertEquals(expected+1, profileService.retriveAll().size());
		userService.delete(aUser);
	}
	
	@Test
	public void testAProfileCanBeSavedWithAlerts(){
		
		Integer expected = profileService.retriveAll().size();
		
		User aUser = new User();
		Profile aProfile = new Profile();
		
		PriceAlert priceAlert = new PriceAlert();
		UmbralAlert umbralAlert = new UmbralAlert();
		umbralAlertService.save(umbralAlert);
		priceAlertService.save(priceAlert);
		
		aProfile.addNewAlert(priceAlert);
		aProfile.addNewAlert(umbralAlert);
		
		profileService.save(aProfile);
		
		aUser.setProfile(aProfile);
		userService.save(aUser);
				
		Assert.assertEquals(expected+1 , profileService.retriveAll().size());
	}
}
