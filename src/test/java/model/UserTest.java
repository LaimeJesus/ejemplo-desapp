package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import builders.UserBuilder;
import util.Address;
import util.Password;

public class UserTest {
	
	@Test
	public void testWhenICreateANewUserThenItHasAProfile(){
		User newUser = new User();
		
		assertTrue(newUser.getProfile() != null);
	}
	
	@Test
	public void testWhenICreateANewUserWithANameAMailAPasswordAndAnAddressThenItHasAllTheseFieldsSet(){
		UserBuilder newUserBuilder = new UserBuilder();
		User okUser = newUserBuilder
				.withUsername("someName")
				.withEmail("someEmail")
				.withPassword(new Password("somePassword"))
				.withAddress(new Address("someAddress"))
				.build();
		assertEquals(okUser.getUsername(), "someName");
		assertEquals(okUser.getEmail(), "someEmail");
		assertEquals(okUser.getPassword().getPassword(), new Password("somePassword").getPassword());
		assertEquals(okUser.getAddress().getAddress(), new Address("someAddress").getAddress());
	}
}
