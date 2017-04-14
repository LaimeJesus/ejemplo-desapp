package model.registers;

import model.products.ProductList;

public abstract class Filter {
	public abstract boolean accepts(ProductList pl);
}
