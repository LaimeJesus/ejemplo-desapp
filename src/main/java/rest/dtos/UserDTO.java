package rest.dtos;

import model.users.User;

public class UserDTO {
	public int id;
	public String username;
	//public ProfileDTO profile;
	
	public User toUser(){
		User u = new User();
		u.setUsername(username);
		return u;
	}
	
}
