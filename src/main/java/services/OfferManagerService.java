package services;

import java.util.ArrayList;
import java.util.List;

import exceptions.OfferIsAlreadyCreatedException;
import model.ProductList;
import model.offers.Offer;

public class OfferManagerService {
	
	
	private List<Offer> allOffers = new ArrayList<Offer>(); //Simulacion de la base 
	
	
	
	public List<Offer> getAllOffers() {
		return allOffers;
	}

	public void setAllOffers(List<Offer> allOffers) {
		this.allOffers = allOffers;
	}
	
	public void applyOffer(Offer aOffer , ProductList aProductList) {
		aProductList.applyOffer(aOffer);
	}
	
	public Boolean isApplicable(Offer aOffer , ProductList aProductList) {
		return aProductList.isApplicable(aOffer);
	}
	
	public void addOffer(Offer newOffer) throws OfferIsAlreadyCreatedException {
		if (this.offerIsCreated(newOffer)) {
			throw new OfferIsAlreadyCreatedException("Ya se encuentra una oferta con las mismas caracteristicas");
		}
		this.saveToRepository(newOffer);
	}
	
	private boolean offerIsCreated(Offer newOffer) {
		return this.getAllOffers().stream().anyMatch(
				offer -> offer.equals(newOffer)
				);
				
	}

	private void saveToRepository(Offer newOffer) {
		this.allOffers.add(newOffer);
	}
	

}
