package services.general;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import builders.UserBuilder;
import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.CombinationOffer;
import model.users.User;
import services.microservices.UserService;
import util.Password;
import util.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class GeneralServiceTest {

	
	
	@Autowired
    @Qualifier("services.general.generalservice")
    private GeneralService generalService;
	
	@Autowired
    @Qualifier("services.microservices.userservice")
    private UserService userService;

	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;
	
	
	@Test
    public void testWhenCreatingNewUserThenUserServiceCanRetriveIt(){
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		
		try {
			Integer expected = userService.retriveAll().size();
			generalService.createUser(userToSignUp);
			
			Assert.assertEquals(expected+1 , userService.retriveAll().size());
			userService.delete(userToSignUp);
		} catch (UserAlreadyExistsException e) {
			fail();
		}
    }
	
	@Test (expected = UserAlreadyExistsException.class)
    public void testWhenCreatingNewUserThatExistThenUserServiceThrowsException() throws UserAlreadyExistsException{
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		User anotherUserToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("otrosandoval@gmail.com")
				.withPassword(new Password("miotrapass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		
		generalService.createUser(userToSignUp);
		generalService.createUser(anotherUserToSignUp);
		
		userService.delete(userToSignUp);
    }
	
	@Test
    public void testWhenAAdminUserCreatesAnOfferThenEverythinIsOkay(){
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj2")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.ADMIN)
				.build();
		Integer expected = generalOfferService.retriveAll().size();
		
		try {
			
			generalService.createUser(userToSignUp);
			generalService.createOffer(new CombinationOffer(), userToSignUp);
			
			Assert.assertEquals(expected+1, generalOfferService.retriveAll().size());
		} catch (UsernameOrPasswordInvalidException | WrongUserPermissionException | UserAlreadyExistsException e) {
			e.printStackTrace();
			fail();
		}
    }
	
	@Test
	public void testPrueba() {
		Assert.assertEquals(Permission.ADMIN , Permission.ADMIN);
	}
	
}
