package repositories;

import model.offers.CategoryOffer;

public class CategoryOfferRepository extends HibernateGenericDAO<CategoryOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9173739835380892629L;

	@Override
	protected Class<CategoryOffer> getDomainClass() {
		return CategoryOffer.class;
	}

}
