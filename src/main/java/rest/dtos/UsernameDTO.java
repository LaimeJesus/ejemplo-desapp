package rest.dtos;

import model.users.User;

public class UsernameDTO {

	public String username;
	
	public UsernameDTO () {
		
	}
	
	public UsernameDTO (User u) {
		this.username = u.getUsername();
	}
	
	public User toUser() {
		User u = new User();
		u.setUsername(this.username);
		return u;
	}
	
}
