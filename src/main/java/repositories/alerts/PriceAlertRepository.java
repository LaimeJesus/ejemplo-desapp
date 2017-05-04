package repositories.alerts;

import model.alerts.PriceAlert;
import repositories.generics.HibernateGenericDAO;

public class PriceAlertRepository extends HibernateGenericDAO<PriceAlert> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1095285496444624488L;

	@Override
	protected Class<PriceAlert> getDomainClass() {
		return PriceAlert.class;
	}

}
