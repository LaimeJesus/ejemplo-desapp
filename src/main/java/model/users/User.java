package model.users;


import model.registers.PurchaseRecord;
import util.Entity;
import util.Password;
import util.Permission;

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
	
	private Permission userPermission;
	
	public User(String newUsername , String newEmail , Password newPassword , Profile newProfile) {
		super();
		this.setUsername(newUsername);
		this.setEmail(newEmail);
		this.setPassword(newPassword);
		this.setProfile(newProfile);
	}
	public User() {
		this.setProfile(new Profile());
		this.setIsLogged(false);
		this.setUserPermission(Permission.NORMAL);
	}
		
	public void setIsLogged(Boolean bool) {
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
	
	public Permission getUserPermission() {
		return userPermission;
	}
	public void setUserPermission(Permission userPermission) {
		this.userPermission = userPermission;
	}
	
	
	
	public void login() {
		this.setIsLogged(true);
	}
	
	public void logout() {
		this.setIsLogged(false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean equals(Object anyObject) {
		
		if (this.isMyType(anyObject)) {
			User newUser = (User) anyObject;
			return this.totalEquals(newUser) ;
		}
		return false;
		
	}
	
	private boolean isMyType(Object anyObject) {
		return anyObject != null && anyObject instanceof User;
	}
	
	private boolean totalEquals(User someUser) {
		return 
			this.getUsername().equals(someUser.getUsername()) &&
			this.getPassword().equals(someUser.getPassword()) &&
			this.getEmail().equals(someUser.getEmail()) &&
			this.getUserPermission().equals(someUser.getUserPermission());
	}
	
	public boolean hasWritePermission () {
		return !this.isNormalUser();
	}
	
	private boolean isNormalUser() {
		return this.getUserPermission().equals(Permission.NORMAL);
	}
	
	
}
