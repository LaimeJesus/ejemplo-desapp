package rest.dtos.offers;

import org.joda.time.Interval;

import rest.dtos.generics.DateDTO;

public class OfferDTO {
	public Integer id;
	public DateDTO start;
	public DateDTO end;
	public Integer discount;
	public String description;
	public OfferDTO(){
		
	}
	public OfferDTO(Integer offerId, Interval interval, Integer discountRate){
		id = offerId;
		start = new DateDTO(interval.getStart());
		end = new DateDTO(interval.getEnd());
		discount = discountRate;
	}
}
