package model;

public class User {
	
	String username;
	String email;
	String password;
	String address;
	
	Profile profile;
	
	
	public User(String newUsername , String newEmail , String newPassword , String newAddress) {
		this.username = newUsername;
		this.email = newEmail;
		this.password = newPassword;
		this.address = newAddress;
	}
	
	

}
