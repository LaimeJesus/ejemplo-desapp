package repositories;

import model.products.Product;

public class ProductRepository extends HibernateGenericDAO<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7892313628132595847L;

	@Override
	protected Class<Product> getDomainClass() {
		return Product.class;
	}

}
