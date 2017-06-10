package util;

public class Password extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -190619487033895009L;
	private String password;

	
	public Password(){
		
	}
	
	public Password(String aPassword) {
		this.setPassword(aPassword);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public boolean equals(Object anyObject) {
		
		if (this.isMyType(anyObject)) {
			Password newPassword = (Password) anyObject;
			return this.totalEquals(newPassword) ;
		}
		return false;
		
	}
	
	private boolean isMyType(Object anyObject) {
		return anyObject != null && anyObject instanceof Password;
	}
	
	private boolean totalEquals(Password somePassword) {
		return 
			this.getPassword().equals(somePassword.getPassword());
	}

	@Override
	public String toString() {
		return "********";
	}
	
}
