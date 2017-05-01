package repositories.offers;

import model.offers.CrossingOffer;
import repositories.HibernateGenericDAO;

public class CrossingOfferRepository extends HibernateGenericDAO<CrossingOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5423380516989481410L;

	@Override
	protected Class<CrossingOffer> getDomainClass() {
		return CrossingOffer.class;
	}

}
