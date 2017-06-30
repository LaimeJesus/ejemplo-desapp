package services.microservices;

import org.springframework.transaction.annotation.Transactional;

import model.users.Profile;

public class ProfileService extends GenericService<Profile>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2834330268087404494L;

	@Transactional
	public Profile find(Profile aProfile) {
		return this.getRepository().findByExample(aProfile).get(0);
	}
	
}
