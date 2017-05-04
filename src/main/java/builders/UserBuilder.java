package builders;

import model.users.User;
import util.Password;
import util.Permission;

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
	
	public UserBuilder withUserPermission(Permission aPermission) {
		this.getUser().setUserPermission(aPermission);
		return this;
	}
}
