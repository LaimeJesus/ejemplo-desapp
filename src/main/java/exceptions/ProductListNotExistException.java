package exceptions;

public class ProductListNotExistException extends Exception {

	public ProductListNotExistException(String msg) {
		super(msg);
	}
	public ProductListNotExistException(){
		super("Product list not exist");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391497450204722438L;

}
