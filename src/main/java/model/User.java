package model;

import java.util.ArrayList;

public class User {
	
	String username;
	String email;
	String password;
	String address;
	
	Profile profile;
	
	ArrayList<ProductList> allLists;
	
	
	public User(String newUsername , String newEmail , String newPassword , String newAddress) {
		this.username = newUsername;
		this.email = newEmail;
		this.password = newPassword;
		this.address = newAddress;
		this.allLists = new ArrayList<ProductList>();
	}
	
	

}
