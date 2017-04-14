package model.recommendations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.products.Product;
import util.Category;

public class SameCategoryRecommendation implements RecommendationStrategy {

	private List<Product> elementsToProcess;
	private Map<Category, List<Product>> categoryMapping;
	
	@Override
	public void obtainData() {
		//TODO
	}

	
	@Override
	public void processData() {
		this.setCategoryMapping(this.initializeStructure());
		for (Product aProduct : this.getElementsToProcess()) {
			List<Product> products = this.getCategoryMapping().get(aProduct.getCategory());
			products.add(aProduct);
			categoryMapping.put(aProduct.getCategory(), products);
		}
	}

	private Map<Category, List<Product>> initializeStructure() {
		Map<Category, List<Product>> newCategoryMapping = new HashMap<Category, List<Product>>();
		for (Category aCategory : Category.values()) {
			newCategoryMapping.put(aCategory, new ArrayList<Product>());
		}
		return newCategoryMapping;
	}

	@Override
	public Recommendation generateRecommendation(Product related , Integer quantity) {
		List<Product> list = this.getCategoryMapping().get(related.getCategory());
		if (list.size() > quantity) {
			return new Recommendation(related, list.subList(0, quantity));
		} return new Recommendation(related, list);
	}

	public List<Product> getElementsToProcess() {
		return elementsToProcess;
	}

	public void setElementsToProcess(List<Product> elementsToProcess) {
		this.elementsToProcess = elementsToProcess;
	}
	
	public Map<Category, List<Product>> getCategoryMapping() {
		return this.categoryMapping;
	}
	
	private void setCategoryMapping( Map<Category, List<Product>> newCategoryMapping) {
		this.categoryMapping = newCategoryMapping;
	}

}
