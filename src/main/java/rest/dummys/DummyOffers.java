package rest.dummys;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.offers.CrossingOffer;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import util.Category;

public class DummyOffers {

	public ArrayList<Offer> example() {
		ArrayList<Offer> alloffers = new ArrayList<Offer>();
		DateTime today = DateTime.now();
		
		Interval validInterval1 = new Interval(today , today.plusDays(5) );
		CategoryOffer offer1 = new CategoryOffer(15, validInterval1, Category.Dairy);
		alloffers.add(offer1);
		
		Interval validInterval2 = new Interval(today.minusDays(2) , today.plusDays(1) );
		CategoryOffer offer2 = new CategoryOffer(25, validInterval2, Category.Baked);
		alloffers.add(offer2);
		
		Interval validInterval3 = new Interval(today.minusDays(1) , today.plusDays(13) );
		CategoryOffer offer3 = new CategoryOffer(55, validInterval3, Category.Cleaning);
		alloffers.add(offer3);
		
		
		List<Product> products = new DummyProduct().example();
		
		Interval validInterval4 = new Interval(today.minusHours(3) , today.plusHours(5) );
		CombinationOffer offer4 = new CombinationOffer(products.get(1), products.get(2), 15, validInterval4);
		alloffers.add(offer4);
		
		Interval validInterval5 = new Interval(today.minusHours(3) , today.plusHours(5) );
		CombinationOffer offer5 = new CombinationOffer(products.get(1), products.get(2), 30, validInterval5);
		alloffers.add(offer5);
		
		Interval validInterval6 = new Interval(today.minusHours(13) , today.plusHours(25) );
		CombinationOffer offer6 = new CombinationOffer(products.get(3), products.get(6), 25, validInterval6);
		alloffers.add(offer6);
		
		CrossingOffer offer7 = new CrossingOffer(15, products.get(1), 2, 1, validInterval3);
		alloffers.add(offer7);
		
		CrossingOffer offer8 = new CrossingOffer(15, products.get(5), 3, 2, validInterval5);
		alloffers.add(offer8);
		
		return alloffers;
	}
	
}
