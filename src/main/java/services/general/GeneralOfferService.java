package services.general;

import java.util.ArrayList;
import java.util.List;

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
	
	public void delete(Offer offer) {
		if (offer instanceof CategoryOffer) {
			getCategoryOfferService().delete( (CategoryOffer) offer);
		}
		if (offer instanceof CombinationOffer) {
			getCombinationOfferService().delete( (CombinationOffer) offer);
		}
		if (offer instanceof CrossingOffer) {
			getCrossingOfferService().delete( (CrossingOffer) offer);
		}
	}
	
	public List<Offer> retriveAll() {
		List<Offer> results = new ArrayList<Offer>();
		results.addAll(this.getCategoryOfferService().retriveAll());
		results.addAll(this.getCrossingOfferService().retriveAll());
		results.addAll(this.getCombinationOfferService().retriveAll());
		return results;
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