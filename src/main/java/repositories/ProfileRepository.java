package repositories;

import model.users.Profile;
import repositories.generics.HibernateGenericDAO;

public class ProfileRepository extends HibernateGenericDAO<Profile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7237461187015837310L;

	@Override
	protected Class<Profile> getDomainClass() {
		return Profile.class;
	}

}
