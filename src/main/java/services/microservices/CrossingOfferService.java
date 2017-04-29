package services.microservices;

import model.offers.CrossingOffer;

public class CrossingOfferService extends GenericService<CrossingOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7589276955191921600L;

	
	
	
	public CrossingOffer getById(Integer id) {
		return this.getRepository().findById(id);
	}
	
}
