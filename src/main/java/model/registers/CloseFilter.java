package model.registers;

import model.products.ProductList;

public class CloseFilter extends Filter{

	@Override
	public boolean accepts(ProductList pl) {
		return false;
	}

}
