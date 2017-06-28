package exceptions;

import model.products.ProductList;

public class CanNotGetCashRegister extends Exception {

	public CanNotGetCashRegister(ProductList aProductList) {
		super("Can not get cash register for " + aProductList.getName());
	}
	private static final long serialVersionUID = -4752037073064600868L;

	public CanNotGetCashRegister(){
		super("No cash register open at this moment");
	}
}
