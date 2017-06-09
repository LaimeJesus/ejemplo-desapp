package rest.dtos.users;

import model.users.User;
import util.Address;
import util.Password;

public class UserDTO {
	public String username;
	public String password;
	public String email;
	public String address;
	//public ProfileDTO profile;
	
	public User toUser(){
		User u = new User();
		u.setUsername(username);
		return u;
	}
	
	public User fullUser(){
		User u = toUser();
		u.setPassword(new Password(password));
		return u;
	}
	
	public User signUpUser(){
		User u = new User();
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(new Password(password));
//		u.getProfile().setAddress(new Address(address));
		return u;
	}
}
