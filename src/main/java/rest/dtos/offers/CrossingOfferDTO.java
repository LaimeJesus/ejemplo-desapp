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
		this.productId = x.getRelatedProduct().getId();
		this.minQuantity = x.getMinQuantity();
		this.maxQuantity = x.getMaxQuantity();		
	}

	public static List<CrossingOfferDTO> createCrossingOffers(List<CrossingOffer> offers) {
		return offers.stream().map((CrossingOffer x) -> new CrossingOfferDTO(x)).collect(Collectors.toList());
	}

	public CrossingOffer toCrossingOffer(Product product) {
		return new CrossingOffer(this.discount, product, this.maxQuantity, this.minQuantity, new Interval(this.start.toDateTime(), this.end.toDateTime()));
	}
}
