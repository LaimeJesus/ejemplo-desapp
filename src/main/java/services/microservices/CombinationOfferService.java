package services.microservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.CombinationOfferNotExistException;
import model.offers.CombinationOffer;
import model.offers.Offer;
import model.products.ProductList;

public class CombinationOfferService extends GenericService<CombinationOffer> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6264043641769715489L;

	@Transactional
	public boolean isOfferValid(Offer someOffer) {
		for (CombinationOffer offer : this.retriveAll()) {
			if (offer.isEqualsToMe(someOffer)) {
				return false;
			}
		}
		return true;
	}
	
	@Transactional
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
	
	@Transactional
	public void createOffer(CombinationOffer combinationOffer) {
		//isOfferValid(combinationOffer);
		save(combinationOffer);
	}
	
	@Transactional
	public CombinationOffer getCombinationOfferById(Integer combinationOfferId) throws CombinationOfferNotExistException {
		CombinationOffer res = findById(combinationOfferId);
		if(res == null) throw new CombinationOfferNotExistException("combination offer with id: " + combinationOfferId+ " does not exist");
		return res;
	}
	
	@Transactional
	public void delete(Integer combinationOfferId) throws CombinationOfferNotExistException {
		delete(getCombinationOfferById(combinationOfferId));
	}
}
