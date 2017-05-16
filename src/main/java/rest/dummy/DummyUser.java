package rest.dummy;

import java.util.ArrayList;
import java.util.List;

import builders.UserBuilder;
import model.users.User;
import util.Password;
import util.Permission;

public class DummyUser {

	public List<User> example(){
		ArrayList<User> res = new ArrayList<User>();
		User jesus = new UserBuilder()
			.withUsername("JesusLaime")
			.withEmail("laimejesu@gmail.com")
			.withPassword(new Password("desapp123"))
			.withUserPermission(Permission.ADMIN)
			.build();
		User lucas = new UserBuilder()
			.withUsername("LucasSandoval")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("desapp123"))
			.withUserPermission(Permission.ADMIN)
			.build();
		res.add(jesus);
		res.add(lucas);
		return res;
	}
}
