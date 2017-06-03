package rest.dtos.offers;

import org.joda.time.Interval;

import model.offers.CombinationOffer;
import model.products.Product;

public class CombinationOfferDTO extends OfferDTO{
	
	public Integer relatedProductId;
	public Integer combinatedProductId;
	
	public CombinationOfferDTO(CombinationOffer combinationOffer) {
		id = combinationOffer.getId();
		relatedProductId = combinationOffer.getRelatedProduct().getId();
		combinatedProductId = combinationOffer.getCombinatedProduct().getId();
		start = new DateDTO(combinationOffer.getValidPeriod().getStart());
		end = new DateDTO(combinationOffer.getValidPeriod().getEnd());
	}

	public CombinationOffer toCombinationOffer(Product relatedProduct, Product combinatedProduct) {
		return new CombinationOffer(relatedProduct, combinatedProduct, discount, new Interval(start.toDateTime(), end.toDateTime()));
	}

}
