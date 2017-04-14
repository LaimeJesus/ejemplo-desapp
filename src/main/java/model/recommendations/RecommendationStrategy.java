package model.recommendations;

import model.products.Product;

public interface RecommendationStrategy {

	public void obtainData();
	public void processData();
	public Recommendation generateRecommendation(Product related , Integer quantity);
}
