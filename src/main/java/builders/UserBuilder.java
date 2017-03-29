package builders;

import model.User;
import util.Address;
import util.Password;

public class UserBuilder {

	private User user;

	public UserBuilder(){
		this.user = new User();
	}
	
	private User getUser() {
		return this.user;
	}

	public UserBuilder withPassword(Password aPassword) {
		this.getUser().setPassword(aPassword);
		return this;
	}

	public UserBuilder withAddress(Address anAddress) {
		this.getUser().setAddress(anAddress);
		return this;
	}

	public User build() {
		return this.getUser();
	}

	public UserBuilder withUsername(String anUsername) {
		this.getUser().setUsername(anUsername);
		return this;
	}

	public UserBuilder withEmail(String anEmail) {
		this.getUser().setEmail(anEmail);
		return this;
	}
}
