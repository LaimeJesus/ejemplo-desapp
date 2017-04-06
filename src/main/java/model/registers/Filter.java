package model.registers;

import model.ProductList;

public abstract class Filter {
	public abstract boolean accepts(ProductList pl);
}
