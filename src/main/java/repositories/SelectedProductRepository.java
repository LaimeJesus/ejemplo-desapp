package repositories;

import model.products.SelectedProduct;

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
