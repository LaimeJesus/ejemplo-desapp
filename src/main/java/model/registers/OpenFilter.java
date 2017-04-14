package model.registers;

import model.products.ProductList;

public class OpenFilter extends Filter {

	@Override
	public boolean accepts(ProductList pl) {
		return true;
	}

}
