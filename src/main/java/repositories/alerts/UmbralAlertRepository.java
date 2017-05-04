package repositories.alerts;

import model.alerts.UmbralAlert;
import repositories.generics.HibernateGenericDAO;

public class UmbralAlertRepository extends HibernateGenericDAO<UmbralAlert>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6959219489220236330L;

	@Override
	protected Class<UmbralAlert> getDomainClass() {
		return UmbralAlert.class;
	}

}
