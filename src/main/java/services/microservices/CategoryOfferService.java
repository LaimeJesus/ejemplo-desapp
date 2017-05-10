package services.microservices;

import model.offers.CategoryOffer;
import model.offers.Offer;

public class CategoryOfferService extends GenericService<CategoryOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6525564083052739423L;

	public boolean isOfferValid(Offer someOffer) {
		for (CategoryOffer offer : this.retriveAll()) {
			if (offer.equals(someOffer)) {
				return true;
			}
		}
		return false;
	}

}
