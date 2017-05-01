package repositories.users;

import model.users.User;
import repositories.generics.HibernateGenericDAO;

public class UserRepository extends HibernateGenericDAO<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5445769667189403593L;

	@Override
	protected Class<User> getDomainClass() {
		return User.class;
	}

}
