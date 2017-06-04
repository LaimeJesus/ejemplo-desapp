package rest.dtos.offers;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;

import model.offers.CrossingOffer;
import model.products.Product;

public class CrossingOfferDTO extends OfferDTO{

	public Integer productId;
	public Integer minQuantity;
	public Integer maxQuantity;

	public CrossingOfferDTO(CrossingOffer x) {
		super(x.getId(), x.getValidPeriod(), x.getDiscountRate());
		productId = x.getRelatedProduct().getId();
		minQuantity = x.getMinQuantity();
		maxQuantity = x.getMaxQuantity();		
	}

	public static List<CrossingOfferDTO> createCrossingOffers(List<CrossingOffer> offers) {
		return offers.stream().map(x -> new CrossingOfferDTO(x)).collect(Collectors.toList());
	}

	public CrossingOffer toCrossingOffer(Product p) {
		return new CrossingOffer(discount, p, maxQuantity, minQuantity, new Interval(start.toDateTime(), end.toDateTime()));
	}
}
