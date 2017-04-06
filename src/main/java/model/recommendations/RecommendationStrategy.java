package model.recommendations;

import model.Product;

public interface RecommendationStrategy {

	public void obtainData();
	public void processData();
	public Recommendation generateRecommendation(Product related , Integer quantity);
}
