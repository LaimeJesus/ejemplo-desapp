package repositories;

import model.recommendations.Recommendation;
import repositories.generics.HibernateGenericDAO;

public class RecommendationRepository extends HibernateGenericDAO<Recommendation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3511426344156899482L;

	@Override
	protected Class<Recommendation> getDomainClass() {
		return Recommendation.class;
	}

}
