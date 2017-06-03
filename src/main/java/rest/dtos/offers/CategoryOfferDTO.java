package rest.dtos.offers;

import org.joda.time.Interval;

import model.offers.CategoryOffer;
import util.Category;

public class CategoryOfferDTO extends OfferDTO{
	public Category category;
	
	public CategoryOffer toCategoryOffer(){
		return new CategoryOffer(discount, new Interval(start.toDateTime(), end.toDateTime()), category);
	}
	
	public CategoryOfferDTO(CategoryOffer fromCat){
		id = fromCat.getId();
		category = fromCat.getCategory();
		start = new DateDTO(fromCat.getValidPeriod().getStart());
		end = new DateDTO(fromCat.getValidPeriod().getEnd());
		discount = fromCat.getDiscountRate();		
	}
}
