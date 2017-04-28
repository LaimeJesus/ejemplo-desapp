package repositories;

import model.products.PurchaseRecord;

public class PurchaseRecordRepository extends HibernateGenericDAO<PurchaseRecord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5424723432565879330L;

	@Override
	protected Class<PurchaseRecord> getDomainClass() {
		return PurchaseRecord.class;
	}

}
