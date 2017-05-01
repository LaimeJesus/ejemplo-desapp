package model.users;


import model.registers.PurchaseRecord;
import util.Entity;
import util.Password;

public class User extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -93676646992378093L;
	private String username;
	private String email;
	private Profile profile;
	private Password password;
	private Boolean isLogged;
	
	public User(String newUsername , String newEmail , Password newPassword , Profile newProfile) {
		this.setUsername(newUsername);
		this.setEmail(newEmail);
		this.setPassword(newPassword);
		this.setProfile(newProfile);	
	}
	public User() {
		this.setProfile(new Profile());
		this.setIsLogged(false);
	}
		
	private void setIsLogged(Boolean bool) {
		this.isLogged = bool;
	}
	
	public Boolean getIsLogged(){
		return this.isLogged;
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
	public void newPurchase(PurchaseRecord aPurchaseRecord) {
		this.getProfile().addNewPurchaseToHistory(aPurchaseRecord);
		
	}
}
