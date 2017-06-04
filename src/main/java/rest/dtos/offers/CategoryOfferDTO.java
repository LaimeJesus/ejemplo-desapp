package rest.dtos.offers;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;

import model.offers.CategoryOffer;
import util.Category;

public class CategoryOfferDTO extends OfferDTO{
	public Category category;
	
	public CategoryOffer toCategoryOffer(){
		return new CategoryOffer(discount, new Interval(start.toDateTime(), end.toDateTime()), category);
	}
	
	public CategoryOfferDTO(CategoryOffer categoryOffer){
		super(categoryOffer.getId(), categoryOffer.getValidPeriod(), categoryOffer.getDiscountRate());
		category = categoryOffer.getCategory();
	}
	
	public static List<CategoryOfferDTO> createCategoryOffers(List<CategoryOffer> categoryOffers) {
		return categoryOffers.stream().map(x -> new CategoryOfferDTO(x)).collect(Collectors.toList());
	}
}
