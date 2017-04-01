package model;

public class CloseFilter extends Filter{

	@Override
	public boolean accepts(ProductList pl) {
		return false;
	}

}
