package services.microservices;

import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.offers.Offer;

public class CombinationOfferService extends GenericService<CombinationOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6264043641769715489L;

	public boolean isOfferValid(Offer someOffer) {
		for (CombinationOffer offer : this.retriveAll()) {
			if (offer.equals(someOffer)) {
				return true;
			}
		}
		return false;
	}
}
