package repositories;

import model.users.User;

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
