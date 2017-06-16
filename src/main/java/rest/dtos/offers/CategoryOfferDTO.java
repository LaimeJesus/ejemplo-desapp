package rest.dtos.offers;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;

import model.offers.CategoryOffer;
import util.Category;

public class CategoryOfferDTO extends OfferDTO{
	public Category category;
	
	public CategoryOffer toCategoryOffer(){
		return new CategoryOffer(this.discount, new Interval(this.start.toDateTime(), this.end.toDateTime()), this.category);
	}
	
	public CategoryOfferDTO(CategoryOffer categoryOffer){
		super(categoryOffer.getId(), categoryOffer.getValidPeriod(), categoryOffer.getDiscountRate());
		this.category = categoryOffer.getCategory();
	}
	
	public static List<CategoryOfferDTO> createCategoryOffers(List<CategoryOffer> categoryOffers) {
		return categoryOffers.stream().map(x -> new CategoryOfferDTO(x)).collect(Collectors.toList());
	}
}
