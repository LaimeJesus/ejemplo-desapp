package model;

public class TotalProductsFilter extends Filter{

	private int totalProducts;

	public TotalProductsFilter(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	@Override
	public boolean accepts(ProductList productList) {
		return productList.getQuantityOfProducts() <= this.totalProducts;
	}

}
