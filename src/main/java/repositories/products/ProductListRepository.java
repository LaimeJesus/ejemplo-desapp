package repositories.products;

import model.products.ProductList;
import repositories.generics.HibernateGenericDAO;

public class ProductListRepository extends HibernateGenericDAO<ProductList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6867000144431548169L;

	@Override
	protected Class<ProductList> getDomainClass() {
		return ProductList.class;
	}

}
