package repositories.offers;

import model.offers.CategoryOffer;
import repositories.generics.HibernateGenericDAO;

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
