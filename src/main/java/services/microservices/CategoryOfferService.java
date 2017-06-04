package services.microservices;

import java.util.ArrayList;
import java.util.List;

import exceptions.CategoryOfferNotExistException;
import model.offers.CategoryOffer;
import model.offers.Offer;
import model.products.ProductList;

public class CategoryOfferService extends GenericService<CategoryOffer> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6525564083052739423L;

	public boolean isOfferValid(Offer someOffer) {
		for (CategoryOffer offer : this.retriveAll()) {
			if (offer.isEqualsToMe(someOffer)) {
				return false;
			}
		}
		return true;
	}

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

	public void delete(Integer categoryOfferId) throws CategoryOfferNotExistException {
		CategoryOffer co = getCategoryOfferById(categoryOfferId);
		delete(co);
	}

	public CategoryOffer getCategoryOfferById(Integer categoryOfferId) throws CategoryOfferNotExistException {
		CategoryOffer co = findById(categoryOfferId);
		if(co == null) throw new CategoryOfferNotExistException("Category Offer with id: "+ categoryOfferId+" does not exist");
		return co;
	}

	public void createOffer(CategoryOffer categoryOffer) {
		//isOfferValid(categoryOffer);
		save(categoryOffer);		
	}
}
