package services.general;

import org.hamcrest.core.IsInstanceOf;

import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.offers.CrossingOffer;
import model.offers.Offer;
import services.microservices.CategoryOfferService;
import services.microservices.CombinationOfferService;
import services.microservices.CrossingOfferService;

public class GeneralOfferService {
	
	
	private CategoryOfferService categoryOfferService;
	private CombinationOfferService combinationOfferService;
	private CrossingOfferService crossingOfferService;
	
	
	
	public void save(Offer offer) {
		if (offer instanceof CategoryOffer) {
			getCategoryOfferService().save( (CategoryOffer) offer);
		}
		if (offer instanceof CombinationOffer) {
			getCombinationOfferService().save( (CombinationOffer) offer);
		}
		if (offer instanceof CrossingOffer) {
			getCrossingOfferService().save( (CrossingOffer) offer);
		}
	}


	
	

	public CategoryOfferService getCategoryOfferService() {
		return categoryOfferService;
	}
	public void setCategoryOfferService(CategoryOfferService categoryOfferService) {
		this.categoryOfferService = categoryOfferService;
	}
	public CombinationOfferService getCombinationOfferService() {
		return combinationOfferService;
	}
	public void setCombinationOfferService(CombinationOfferService combinationOfferService) {
		this.combinationOfferService = combinationOfferService;
	}
	public CrossingOfferService getCrossingOfferService() {
		return crossingOfferService;
	}
	public void setCrossingOfferService(CrossingOfferService crossingOfferService) {
		this.crossingOfferService = crossingOfferService;
	}
}
