package services;

import model.Product;
import model.recommendations.Recommendation;
import model.recommendations.RecommendationStrategy;

public class RecommendationService {

	private RecommendationStrategy strategy;
	private Integer maxRecommended = 4;
	
	public Recommendation getRecommendation(Product aProduct) {
		getStrategy().obtainData();
		getStrategy().processData();
		return getStrategy().generateRecommendation(aProduct,this.getMaxRecommended());
	}


	public RecommendationStrategy getStrategy() {
		return strategy;
	}


	public void setStrategy(RecommendationStrategy strategy) {
		this.strategy = strategy;
	}


	public Integer getMaxRecommended() {
		return maxRecommended;
	}


	public void setMaxRecommended(Integer maxRecommended) {
		this.maxRecommended = maxRecommended;
	}
	
}
