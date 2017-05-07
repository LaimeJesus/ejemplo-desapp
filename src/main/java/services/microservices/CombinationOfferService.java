package services.microservices;

import java.util.ArrayList;
import java.util.List;

import model.offers.CombinationOffer;
import model.products.ProductList;

public class CombinationOfferService extends GenericService<CombinationOffer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6264043641769715489L;

	public List<CombinationOffer> applicableForList(ProductList aProductList) {
		List<CombinationOffer> results = new ArrayList<CombinationOffer>();
		List<CombinationOffer> possibles = this.retriveAll();
		for (CombinationOffer offer : possibles) {
			if (offer.meetRequirements(aProductList)){
				results.add(offer);
			}
		}
		return results;
	}
	
}
