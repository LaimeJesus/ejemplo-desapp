package model.users;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import builders.UserBuilder;
import model.users.User;
import util.Password;

public class UserTest {
	
	@Test
	public void testWhenICreateANewUserThenItHasAProfile(){
		User newUser = new User();
		
		Assert.assertNotNull(newUser.getProfile());
		Assert.assertFalse(newUser.getIsLogged());
	}
	
	@Test
	public void testWhenICreateANewUserWithANameAMailAPasswordAndAnAddressThenItHasAllTheseFieldsSet(){
		UserBuilder newUserBuilder = new UserBuilder();
		User okUser = newUserBuilder
				.withUsername("someName")
				.withEmail("someEmail")
				.withPassword(new Password("somePassword"))
				.build();
		assertEquals(okUser.getUsername(), "someName");
		assertEquals(okUser.getEmail(), "someEmail");
		assertEquals(okUser.getPassword().getPassword(), new Password("somePassword").getPassword());
	}
}
