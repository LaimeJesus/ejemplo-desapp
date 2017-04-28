package repositories;

import model.offers.CombinationOffer;

public class CombinationOfferRepository extends HibernateGenericDAO<CombinationOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3281179141290494446L;

	@Override
	protected Class<CombinationOffer> getDomainClass() {
		return CombinationOffer.class;
	}

}
