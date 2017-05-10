package repositories;

import repositories.generics.HibernateGenericDAO;
import util.Money;

public class MoneyRepository extends HibernateGenericDAO<Money> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3846074298799954052L;

	@Override
	protected Class<Money> getDomainClass() {
		return Money.class;
	}

}
