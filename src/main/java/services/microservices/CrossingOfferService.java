package services.microservices;

import model.offers.CrossingOffer;
import model.offers.Offer;

public class CrossingOfferService extends GenericService<CrossingOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7589276955191921600L;

	
	
	
	public CrossingOffer getById(Integer id) {
		return this.getRepository().findById(id);
	}




	public boolean isOfferValid(Offer someOffer) {
		for (CrossingOffer offer : this.retriveAll()) {
			if (offer.equals(someOffer)) {
				return true;
			}
		}
		return false;
	}
	
}
