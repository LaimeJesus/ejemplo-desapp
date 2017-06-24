package rest.dtos;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import model.offers.CategoryOffer;
import util.Category;

public class CategoryOfferDTO {
	
	public Integer discount;
	public String startDate;
	public String endDate;
	public String category;
	public UsernameDTO user;
	
	public CategoryOffer toCategory() {
		CategoryOffer offer = new CategoryOffer();
		offer.setCategory(Category.toCategory(this.category));
		offer.setDiscountRate(this.discount);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		DateTime start = DateTime.parse(this.startDate, formatter);
		DateTime end = DateTime.parse(this.endDate, formatter);
		Interval interval = new Interval(start,end);
		offer.setValidPeriod(interval);
		
		return offer;
	}
	

}
