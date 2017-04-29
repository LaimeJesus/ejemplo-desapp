package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.users.Profile;
import services.microservices.ProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ProfileServiceTest {
	
	@Autowired
	@Qualifier("services.microservices.profileservice")
	private ProfileService profileService;
	
	@Test
	public void testProfilesCanBeAdded(){
		profileService.save(new Profile());
		Assert.assertEquals(1, profileService.retriveAll().size());
	}
}
