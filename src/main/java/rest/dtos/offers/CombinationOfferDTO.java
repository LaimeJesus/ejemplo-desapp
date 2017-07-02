package rest.dtos.offers;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;

import model.offers.CombinationOffer;
import model.products.Product;

public class CombinationOfferDTO extends OfferDTO{
	
	public Integer relatedProductId;
	public Integer combinatedProductId;
	
	public CombinationOfferDTO(CombinationOffer combinationOffer) {
		super(combinationOffer.getId(), combinationOffer.getValidPeriod(), combinationOffer.getDiscountRate());
		this.relatedProductId = combinationOffer.getRelatedProduct().getId();
		this.combinatedProductId = combinationOffer.getCombinatedProduct().getId();
		this.description = combinationOffer.toString();
	}

	public CombinationOffer toCombinationOffer(Product relatedProduct, Product combinatedProduct) {
		return new CombinationOffer(relatedProduct, combinatedProduct, this.discount, new Interval(this.start.toDateTime(), this.end.toDateTime()));
	}

	public static List<OfferDTO> createCombinationOffers(List<CombinationOffer> offers) {
		return offers.stream().map((CombinationOffer x) -> new CombinationOfferDTO(x)).collect(Collectors.toList());
	}
}
