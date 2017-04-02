package model;

import util.Password;

public class User {
	
	private String username;
	private String email;
	private Profile profile;	
	private Password password;
	
	
	public User(String newUsername , String newEmail , Password newPassword , Profile newProfile) {
		this.setUsername(newUsername);
		this.setEmail(newEmail);
		this.setPassword(newPassword);
		this.setMyInitialProfile(newProfile);	
	}
	public User() {
		this.profile = new Profile();
	}
	
	
	
	private void setMyInitialProfile(Profile newProfile) {
		this.setProfile(newProfile);
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

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}
}
