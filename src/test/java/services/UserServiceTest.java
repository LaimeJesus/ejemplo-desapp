package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.users.User;
import services.microservices.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class UserServiceTest {

	@Autowired
	@Qualifier("services.microservices.userservice")
	private UserService userService;

	@Test
	public void testUsersCanBeSaved(){
		userService.save(new User());
		Assert.assertEquals(1, userService.retriveAll().size());
	}

}
