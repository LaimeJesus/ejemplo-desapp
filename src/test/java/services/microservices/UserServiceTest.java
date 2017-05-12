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
	public void testCreateDifferentUsersWithTheSamePassword() throws UserAlreadyExistsException{
		User userOne = new User();
		userOne.setUsername("pepe");
		Password passOne = new Password("pass");
		userOne.setPassword(passOne);
		User userTwo = new User();
		userTwo.setUsername("pisho");
		Password passTwo = new Password("pass");
		userTwo.setPassword(passTwo);
		
		userService.createNewUser(userOne);
		userService.createNewUser(userTwo);
		
		User fromBDOne = userService.findByUsername("pepe");
		User fromBDTwo = userService.findByUsername("pisho");
		
		Assert.assertEquals(fromBDOne.getPassword().getPassword(), fromBDTwo.getPassword().getPassword());
		
		userService.deleteAll();
		
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
