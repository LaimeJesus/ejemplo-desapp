package services.microservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import exceptions.UserAlreadyExistsException;
import exceptions.UsernameDoesNotExistException;
import model.users.User;
import services.microservices.UserService;
import util.Password;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class UserServiceTest {

	@Autowired
	@Qualifier("services.microservices.userservice")
	private UserService userService;

	@Before
	public void setUp() {
		userService.deleteAll();
	}
	
	@Test
	public void testUsersCanBeSavedAndDeleted(){
		Integer expected = userService.count();
		User anUser = new User();
		userService.save(anUser);
		Assert.assertEquals(expected+1, userService.retriveAll().size());
		userService.delete(anUser);
	}

	
	@Test
	public void testUsersCanBeLogged() throws UserAlreadyExistsException, UsernameDoesNotExistException{
		userService.deleteAll();
		User user = new User();
		user.setUsername("pepe");
		user.setEmail("pepe@gmail");
		user.setPassword(new Password("pepe"));
		userService.createNewUser(user);
		Assert.assertFalse(userService.findByUsername("pepe").getIsLogged());
		userService.loginUser(user);
		Assert.assertTrue(userService.findByUsername("pepe").getIsLogged());
		
		userService.delete(user);
	}
	
}
