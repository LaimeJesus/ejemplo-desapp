package model.registers;

import model.products.ProductList;
import util.Money;

public class TotalCostFilter extends Filter{

	private Money totalCost;

	public TotalCostFilter(Money totalCost){
		this.totalCost = totalCost;
	}

	@Override
	public boolean accepts(ProductList aProductList) {
		return aProductList.getTotalAmount().lesserThan(this.totalCost) || aProductList.getTotalAmount().equals(this.totalCost);
	}
}
