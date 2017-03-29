package model;

import java.util.ArrayList;

import util.Address;
import util.Password;

public class User {
	
	private String username;
	private String email;

	private Profile profile;	
	private Address address;
	private Password password;
	private ArrayList<ProductList> allLists;
	
	
	public User(String newUsername , String newEmail , Password newPassword , Address newAddress) {
		this.setUsername(newUsername);
		this.setEmail(newEmail);
		this.setPassword(newPassword);
		this.setAddress(newAddress);
		this.setMyInitialProfile();	
	}

	public User() {
		this.setMyInitialProfile();
	}
	
	private void setMyInitialProfile() {
		this.setAllLists(new ArrayList<ProductList>());
		this.setProfile(new Profile());
	}


	public Profile getProfile() {
		return this.profile;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public Password getPassword() {
		return password;
	}


	public void setPassword(Password password) {
		this.password = password;
	}

	public ArrayList<ProductList> getAllLists() {
		return allLists;
	}

	public void setAllLists(ArrayList<ProductList> allLists) {
		this.allLists = allLists;
	}

}
