package repositories;

import model.products.SelectedProduct;
import repositories.generics.HibernateGenericDAO;

public class SelectedProductRepository extends HibernateGenericDAO<SelectedProduct> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4422042288487184676L;

	@Override
	protected Class<SelectedProduct> getDomainClass() {
		return SelectedProduct.class;
	}

}
