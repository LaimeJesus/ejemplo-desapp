package rest.dummys;

import java.util.ArrayList;
import java.util.List;

import builders.UserBuilder;
import model.users.User;
import util.Address;
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
		jesus.getProfile().setAddress(new Address("calle 147 2643 berazategui"));
		User lucas = new UserBuilder()
			.withUsername("LucasSandoval")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("desapp123"))
			.withUserPermission(Permission.ADMIN)
			.build();
		lucas.getProfile().setAddress(new Address("calle 27 3156 berazategui"));
		res.add(jesus);
		res.add(lucas);
		return res;
	}
}
