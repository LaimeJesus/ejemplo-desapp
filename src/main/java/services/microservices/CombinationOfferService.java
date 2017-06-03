package services.microservices;

import java.util.ArrayList;
import java.util.List;

import exceptions.CombinationOfferNotExistException;
import model.offers.CombinationOffer;
import model.offers.Offer;
import model.products.ProductList;

public class CombinationOfferService extends GenericService<CombinationOffer> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6264043641769715489L;

	public boolean isOfferValid(Offer someOffer) {
		for (CombinationOffer offer : this.retriveAll()) {
			if (offer.isEqualsToMe(someOffer)) {
				return false;
			}
		}
		return true;
	}
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
	public void createOffer(CombinationOffer combinationOffer) {
		save(combinationOffer);
	}
	public CombinationOffer getCombinationOfferById(Integer combinationOfferId) throws CombinationOfferNotExistException {
		CombinationOffer res = findById(combinationOfferId);
		if(res == null) throw new CombinationOfferNotExistException("combination offer with id: " + combinationOfferId+ " does not exist");
		return res;
	}
	public void delete(Integer combinationOfferId) throws CombinationOfferNotExistException {
		CombinationOffer res = getCombinationOfferById(combinationOfferId);
		delete(res);
	}
}
