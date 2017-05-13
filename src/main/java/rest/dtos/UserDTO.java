package rest.dtos;

import model.users.User;
import util.Password;

public class UserDTO {
	public String username;
	public String password;
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
}
