package services.microservices;

import model.users.Profile;

public class ProfileService extends GenericService<Profile>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2834330268087404494L;

	public Profile find(Profile aProfile) {
		return this.getRepository().findByExample(aProfile).get(0);
	}
	
}
