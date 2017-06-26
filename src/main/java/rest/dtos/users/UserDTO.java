package rest.dtos.users;

import java.util.List;
import java.util.stream.Collectors;

import model.users.User;
import util.Password;
import util.Permission;

public class UserDTO {
	public int id;
	public String username;
	public Password password;
	public String email;
	public ProfileDTO profile;
	public Permission role;
	
	public User toUser(){
		User u = new User();
		u.setUsername(username);
		return u;
	}
	
	public User fullUser(){
		User u = toUser();
		u.setPassword(password);
		return u;
	}
	
	public User signUpUser(){
		User u = new User();
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password);
		u.getProfile().setAddress(profile.address);;
		return u;
	}
	
	public UserDTO(User u){
		id = u.getId();
		profile = new ProfileDTO(u.getProfile());
		email = u.getEmail();
		username = u.getUsername();
		password = u.getPassword();
		role = u.getUserPermission();
	}

	public static List<UserDTO> createUsers(List<User> users) {
		return users.stream().map((User x) -> new UserDTO(x)).collect(Collectors.toList());
	}
	
}
