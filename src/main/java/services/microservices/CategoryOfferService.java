package services.microservices;

import java.util.ArrayList;
import java.util.List;

import model.offers.CategoryOffer;
import model.products.ProductList;

public class CategoryOfferService extends GenericService<CategoryOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6525564083052739423L;

	public List<CategoryOffer> applicableForList(ProductList aProductList) {
		List<CategoryOffer> results = new ArrayList<CategoryOffer>();
		List<CategoryOffer> possibles = this.retriveAll();
		for (CategoryOffer offer : possibles) {
			if (offer.meetRequirements(aProductList)){
				results.add(offer);
			}
		}
		return results;
	}

}
