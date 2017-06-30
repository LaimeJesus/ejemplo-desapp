package services.microservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.CrossingOfferNotExistException;
import model.offers.CrossingOffer;
import model.offers.Offer;
import model.products.ProductList;

public class CrossingOfferService extends GenericService<CrossingOffer> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7589276955191921600L;



	@Transactional
	public CrossingOffer getById(Integer id) {
		return this.getRepository().findById(id);
	}



	@Transactional
	public boolean isOfferValid(Offer someOffer) {
		for (CrossingOffer offer : this.retriveAll()) {
			if (offer.isEqualsToMe(someOffer)) {
				return false;
			}
		}
		return true;
	}
	@Transactional
	public List<CrossingOffer> applicableForList(ProductList aProductList) {
		List<CrossingOffer> results = new ArrayList<CrossingOffer>();
		List<CrossingOffer> possibles = this.retriveAll();
		for (CrossingOffer offer : possibles) {
			if (offer.meetRequirements(aProductList)){
				results.add(offer);
			}
		}
		return results;
	}

	@Transactional
	public void delete(Integer crossingOfferId) throws CrossingOfferNotExistException {
		delete(getCrossingOfferById(crossingOfferId));
	}



	@Transactional
	public CrossingOffer getCrossingOfferById(Integer crossingOfferId) throws CrossingOfferNotExistException {
		CrossingOffer co = findById(crossingOfferId);
		if(co == null) throw new CrossingOfferNotExistException("crossing offer with id:" + crossingOfferId+ " does not exist");
		return co;
	}



	@Transactional
	public void createOffer(CrossingOffer crossingOffer) {
//		isOfferValid(crossingOffer);
		save(crossingOffer);
	}

}
